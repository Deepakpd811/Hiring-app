package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.LoginDto;
import com.bridgelab.hiringapp.dto.OtpDto;
import com.bridgelab.hiringapp.dto.RegisterDto;
import com.bridgelab.hiringapp.entity.User;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.exception.EmailAlreadyExistException;
import com.bridgelab.hiringapp.exception.InvalidException;
import com.bridgelab.hiringapp.repository.UserRepository;
import com.bridgelab.hiringapp.utils.JwtUtil;
import com.bridgelab.hiringapp.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("User already registered");
        }


        String otp = otpUtil.generateOtp();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .otp(otp)
                .isOtpVerified(false)
                .otpGeneratedAt(LocalDateTime.now())
                .build();

        otpUtil.sendOtpEmail(user.getEmail(), otp);

        userRepository.save(user);

        return "User registered successfully";

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

    public String otpVerify(Long id, OtpDto otpDto) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new CandidateNotFoundException(id,"User not found");
        }

        String otp = otpDto.getOtp();
        System.out.println(otp);

        boolean otpVerification = otpUtil.verifyOtp(user.get().getOtp(), otp, user.get().getOtpGeneratedAt());

        if (otpVerification) {
            user.get().setOtpVerified(true);
            userRepository.save(user.get());
            return "User otp Verified  successfully";
        }

        return "User otp not Verified ";
    }


}
