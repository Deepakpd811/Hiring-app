package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.*;
import com.bridgelab.hiringapp.entity.User;
import com.bridgelab.hiringapp.exception.EmailAlreadyExistException;
import com.bridgelab.hiringapp.exception.InvalidException;
import com.bridgelab.hiringapp.exception.UserNotFoundException;
import com.bridgelab.hiringapp.repository.UserRepository;
import com.bridgelab.hiringapp.utils.JwtUtil;
import com.bridgelab.hiringapp.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Autowired
    private OtpUtil otpUtil;

    public String register(RegisterDto request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        String otp = otpUtil.generateOtp();
        LocalDateTime now = LocalDateTime.now();

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // If OTP is still valid, throw exception
            if (existingUser.getOtpGeneratedAt().plusMinutes(5).isAfter(now)) {
                throw new EmailAlreadyExistException("User already registered and OTP still valid.");
            }

            // OTP expired, resend
            existingUser.setOtp(otp);
            existingUser.setOtpGeneratedAt(now);
            existingUser.setOtpVerified(false);
            otpUtil.sendOtpEmail(existingUser.getEmail(), otp);
            userRepository.save(existingUser);

            return "OTP expired. New OTP sent.";
        }

        // Register new user
        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .otp(otp)
                .isOtpVerified(false)
                .otpGeneratedAt(now)
                .build();

        otpUtil.sendOtpEmail(newUser.getEmail(), otp);
        userRepository.save(newUser);

        return "User registered successfully. OTP sent.";
    }



    public Map<String, String> login(LoginDto request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));


        if (!user.isOtpVerified()) {
            throw new InvalidException("OTP is not verified. Please verify your email before logging in.");
        }


        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(userDetails);

        return Map.of("token", jwt);
    }

    public String otpVerify(OtpDto otpDto)  {

        Optional<User> userEL = userRepository.findByEmail(otpDto.getEmail());

        if(userEL.isEmpty()){
            throw new UserNotFoundException("User Not found");
        }

        User user = userEL.get();

        boolean otpVerification = otpUtil.verifyOtp(user.getOtp(), otpDto.getOtp(), user.getOtpGeneratedAt());

        if (otpVerification) {
            user.setOtpVerified(true);
            userRepository.save(user);
            return "User otp Verified  successfully";
        }

        return "User otp not Verified ";
    }

    // send otp forgot password
    public String forgotPassword(ForgotPasswordDto fdto)  {

        User user = userRepository.findByEmail(fdto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpGeneratedAt(LocalDateTime.now());
        user.setOtpVerified(false);

        userRepository.save(user);
        otpUtil.sendOtpEmail(fdto.getEmail(), otp);

        return "User otp not Verified ";
    }

    public String resetPassword(ResetPasswordDto rdto)  {

        User user = userRepository.findByEmail(rdto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (!user.isOtpVerified()) {
            throw new InvalidException("OTP is not verified");
        }

        user.setPassword(passwordEncoder.encode(rdto.getNewPassword()));
        user.setOtp(null); // Invalidate OTP
        user.setOtpVerified(true);
        user.setOtpGeneratedAt(null);
        userRepository.save(user);

        return "User otp not Verified ";
    }


}
