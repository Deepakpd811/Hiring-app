package com.bridgelab.hiringapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordDto {
    @NotNull(message = "Email cant be empty")
    private String email;

    @NotNull(message = "new password cant be empty")
    private String newPassword;

}
