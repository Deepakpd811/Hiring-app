package com.bridgelab.hiringapp.listener;


import com.bridgelab.hiringapp.dto.EmailDto;
import com.bridgelab.hiringapp.service.JobOfferEmailService;
import com.bridgelab.hiringapp.utils.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageConsumer {

    @Autowired
    private SendMailUtil email;

    @RabbitListener(queues = "job.offer.notification.queue")
    public void receiveMessage(EmailDto emailDTO) {
       log.info("Received message: " + emailDTO);

        //  Call the EmailService to send the email.
        email.sendEmail(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
        System.out.println("Email sent successfully.");
    }

    @RabbitListener(queues = "otp_queue")
    public void receiveOtpEmail(EmailDto emailDTO) {
        try {
            log.info("Received OTP email: " + emailDTO);

            email.sendEmail(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
            System.out.println("OTP email sent successfully.");
        } catch (Exception e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
        }
    }

}