package com.bridgelab.hiringapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JobOfferNotificationDTO {

    private boolean sent;

    @Min(value = 0, message = "Retry count cannot be negative")
    private int retryCount;

    @Size(max = 100, message = "Message cannot exceed 100 characters")
    @NotNull(message = "Message is required")
    private String message;
}

