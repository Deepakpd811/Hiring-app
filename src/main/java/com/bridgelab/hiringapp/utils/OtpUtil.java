package com.bridgelab.hiringapp.utils;

import com.bridgelab.hiringapp.dto.EmailDto;
import com.bridgelab.hiringapp.exception.EmailSendFailedException;
import com.bridgelab.hiringapp.exception.InvalidException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.otp.exchange}")
    private String otpExchange;

    @Value("${rabbitmq.otp.routing-key}")
    private String otpRoutingKey;

    // generate 6 digit otp
    public String generateOtp(){
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String to, String otp){

        String subject = "OTP Verification";
        String body = "Your OTP for verification is: " + otp;

        EmailDto emailDto = EmailDto.builder()
                .to(to)
                .body(body)
                .subject(subject)
                .build();

        try {

        rabbitTemplate.convertAndSend(otpExchange,otpRoutingKey,emailDto);

        } catch (AmqpException e) {
            throw new EmailSendFailedException("Fail to send otp");
        }

    }

    public boolean verifyOtp(String userOtp, String mailOtp, LocalDateTime otpGeneratedAt) {

        System.out.println("mail "+mailOtp);
        System.out.println("user "+ userOtp);

        if (userOtp == null || !userOtp.equals(mailOtp)) {
            throw new InvalidException("Invalid OTP");
        }

        if (otpGeneratedAt.plusMinutes(5).isBefore(LocalDateTime.now())) {
            throw new InvalidException("OTP expired");
        }

        return true;
    }

}
