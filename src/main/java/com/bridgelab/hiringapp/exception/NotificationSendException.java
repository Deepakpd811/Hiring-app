package com.bridgelab.hiringapp.exception;

import org.springframework.amqp.AmqpException;

public class NotificationSendException extends RuntimeException{
    public NotificationSendException(String msg, AmqpException e){
        super(msg);
    }
}
