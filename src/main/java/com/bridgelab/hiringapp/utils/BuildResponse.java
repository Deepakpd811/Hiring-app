package com.bridgelab.hiringapp.utils;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;

public class BuildResponse {
    BuildResponse(){}

    public static ResponseEntity<ApiResponseDto> success(Object data, String message, String path){

        ApiResponseDto response = ApiResponseDto.builder()
                .data(data)
                .message(message)
                .status(HttpStatus.OK.toString())
                .timestamp(LocalTime.now().toString())
                .path(path)
                .build();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}
