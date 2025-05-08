package com.bridgelab.hiringapp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponseDto {
    private String timestamp;
    private String status;
    private String message;
    private String path;
    private String error;
    private Object data;
}
