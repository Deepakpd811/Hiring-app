package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.dto.LoginDto;
import com.bridgelab.hiringapp.dto.RegisterDto;
import com.bridgelab.hiringapp.service.AuthService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
