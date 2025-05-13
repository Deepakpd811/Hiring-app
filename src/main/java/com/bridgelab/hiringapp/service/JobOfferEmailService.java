package com.bridgelab.hiringapp.service;


import com.bridgelab.hiringapp.dto.EmailDto;
import com.bridgelab.hiringapp.dto.JobOfferNotificationDTO;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.entity.JobOfferNotification;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.exception.NotificationSendException;
import com.bridgelab.hiringapp.repository.CandidateRepository;
import com.bridgelab.hiringapp.repository.JobOfferNotificationRepository;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferEmailService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobOfferNotificationRepository jobOfferNotificationRepository;

    public JobOfferNotification producerSendNotification(Long id) {

        Candidate candidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id, " not found"));

        // job offer table notification
        JobOfferNotification notification; // entity

        // Email info
        EmailDto emailDto = EmailDto.builder()
                .subject("Email for job offer")
                .body("your current status for applied job " + candidate.getStatus())
                .to(candidate.getEmail())
                .build();


        try {
            rabbitTemplate.convertAndSend(
                    "job.offer.notification.exchange",
                    "job.offer.notification",
                    emailDto
            );

            // If successful, mark as sent
            JobOfferNotificationDTO dto =
                    JobOfferNotificationDTO.builder()
                            .sent(true)
                            .message(emailDto.getBody())
                            .retryCount(0)
                            .build();

            notification = new JobOfferNotification(dto, candidate); // set the candidate

        } catch (AmqpException e) {
            System.err.println("Error sending message to RabbitMQ: " + e.getMessage());

            // On failure, mark as not sent and include error message
            JobOfferNotificationDTO dto = JobOfferNotificationDTO.builder()
                    .sent(false).
                    message("fail to update").
                    retryCount(1)
                    .build();
            notification = new JobOfferNotification(dto, candidate);  // set the candidate in  table

            // save the fail
            jobOfferNotificationRepository.save(notification);
            throw new NotificationSendException("Failed to send job offer notification", e);
        }

        // Save successful notification attempt


        return jobOfferNotificationRepository.save(notification);
    }


}