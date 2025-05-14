package com.bridgelab.hiringapp.exception;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler   {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponseDto response = ApiResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message("Validation valid")
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler
    public ResponseEntity<ApiResponseDto> CandidateNotFoundException(CandidateNotFoundException ex, HttpServletRequest request) {

        ApiResponseDto err = ApiResponseDto.builder()
                .error("Bad Request")
                .message("Candidate with id " + ex.getId() + " does not exist")
                .status(HttpStatus.NOT_FOUND.toString())
                .path(request.getRequestURI())
                .timestamp(LocalTime.now().toString())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseDto> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        ApiResponseDto err = ApiResponseDto.builder()
                .error("Bad Request")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.toString())
                .path(request.getRequestURI())
                .timestamp(LocalTime.now().toString())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseDto> NotificationSendException(NotificationSendException ex, HttpServletRequest request) {

        ApiResponseDto err = ApiResponseDto.builder()
                .error("Bad Request")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.toString())
                .path(request.getRequestURI())
                .timestamp(LocalTime.now().toString())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiResponseDto> handleCustomBadRequest(EmailAlreadyExistException ex, HttpServletRequest request) {
        ApiResponseDto response = ApiResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailSendFailedException.class)
    public ResponseEntity<ApiResponseDto> emailFailedException(EmailSendFailedException ex, HttpServletRequest request) {
        ApiResponseDto response = ApiResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

 @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiResponseDto> invalidException(InvalidException ex, HttpServletRequest request) {
        ApiResponseDto response = ApiResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDto> UserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ApiResponseDto response = ApiResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
