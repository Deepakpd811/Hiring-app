package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.*;
import com.bridgelab.hiringapp.service.AuthService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(HttpServletRequest request,@Valid @RequestBody RegisterDto requestdto) {
        String msg = authService.register(requestdto);
        return BuildResponse.success(null,msg,request.getRequestURI());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(HttpServletRequest request,@Valid @RequestBody LoginDto requestdto) {
        Map<String, String > data =authService.login(requestdto);
        return BuildResponse.success(data,"Token generated", request.getRequestURI());
    }

    @PostMapping("/otp-verify")
    public ResponseEntity<ApiResponseDto> verify(HttpServletRequest request, @Valid @RequestBody OtpDto otpDto) {
        System.out.println("otp verified");
        String msg =authService.otpVerify(otpDto);
        return BuildResponse.success(null,msg, request.getRequestURI());
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponseDto> forgotPassword(HttpServletRequest request, @Valid @RequestBody ForgotPasswordDto dto) {
        String msg =authService.forgotPassword(dto);
        return BuildResponse.success(null,msg, request.getRequestURI());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDto> forgotPassword(HttpServletRequest request, @Valid @RequestBody ResetPasswordDto dto) {
        String msg =authService.resetPassword(dto);
        return BuildResponse.success(null,msg, request.getRequestURI());
    }
}
