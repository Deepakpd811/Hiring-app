package com.bridgelab.hiringapp.exception;

public class UserNotFoundException extends RuntimeException{
   public UserNotFoundException(String msg){
        super(msg);
    }
}
