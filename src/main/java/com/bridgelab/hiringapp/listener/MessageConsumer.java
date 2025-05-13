package com.bridgelab.hiringapp.listener;


import com.bridgelab.hiringapp.dto.EmailDto;
import com.bridgelab.hiringapp.service.JobOfferEmailService;
import com.bridgelab.hiringapp.utils.SendMailUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @Autowired
    private SendMailUtil email;

    @RabbitListener(queues = "job.offer.notification.queue")
    public void receiveMessage(EmailDto emailDTO) {
        System.out.println("Received message: " + emailDTO); //  Log

        //  Call the EmailService to send the email.
        email.sendEmail(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
        System.out.println("Email sent successfully.");
    }
}