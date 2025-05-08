package com.bridgelab.hiringapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankInfoDto {

    @NotBlank(message = "Bank name is required")
    @Size(max = 50, message = "Bank name must be at most 50 characters")
    private String bankName;

    @NotBlank(message = "Account number is required")
    @Size(max = 50, message = "Account number must be at most 50 characters")
    private String accountNumber;

    @NotBlank(message = "IFSC code is required")
    @Size(max = 20, message = "IFSC code must be at most 20 characters")
    private String ifscCode;
}
