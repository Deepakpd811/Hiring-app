package com.bridgelab.hiringapp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    @NotNull(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    private String to;

    @NotNull(message = "Email subject is required")
    @Size(min = 1, message = "Subject cannot be empty")
    private String subject;

    @NotNull(message = "Email body is required")
    @Size(min = 1, message = "Body cannot be empty")
    private String body;

}

