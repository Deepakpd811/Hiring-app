package com.bridgelab.hiringapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgotPasswordDto {
    @NotNull(message = "Email cant be empty")
    private String email;
}
