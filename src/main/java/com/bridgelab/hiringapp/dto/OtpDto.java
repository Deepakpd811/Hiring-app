package com.bridgelab.hiringapp.dto;

import com.bridgelab.hiringapp.entity.Candidate;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OtpDto {

    @NotNull(message = "Email cant be empty")
    private String email;

    @NotNull(message = "OTP cant be empty")
    private String otp;
}
