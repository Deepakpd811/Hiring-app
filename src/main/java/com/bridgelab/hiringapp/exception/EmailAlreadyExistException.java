package com.bridgelab.hiringapp.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
