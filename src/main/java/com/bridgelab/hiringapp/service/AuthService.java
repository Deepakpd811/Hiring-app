package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.LoginDto;
import com.bridgelab.hiringapp.dto.RegisterDto;
import com.bridgelab.hiringapp.entity.User;
import com.bridgelab.hiringapp.exception.EmailAlreadyExistException;
import com.bridgelab.hiringapp.repository.UserRepository;
import com.bridgelab.hiringapp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public String register(RegisterDto request) {
        try {
            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();

            userRepository.save(user);
            return "User registered successfully";

        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistException("Email already in use");
        }
    }


    public Map<String, String> login(LoginDto request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        return Map.of("token", jwt);
    }
}
