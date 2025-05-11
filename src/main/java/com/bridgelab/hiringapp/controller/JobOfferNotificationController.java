package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.dto.EmailDto;
import com.bridgelab.hiringapp.entity.JobOfferNotification;
import com.bridgelab.hiringapp.service.JobOfferEmailService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/api/candidates")
@Slf4j
public class JobOfferNotificationController {


    @Autowired
    private JobOfferEmailService jobOfferEmailService;

    //  Endpoint to trigger sending a job offer notification.  It receives an
    //  EmailDTO in the request body.  The @Valid annotation ensures that the
    //  DTO is validated before being processed.
    @PostMapping("/{id}/job-offer")
    public ResponseEntity<ApiResponseDto> sendJobOfferNotification(@PathVariable Long id,
                                                                   HttpServletRequest request) {

        log.info("Send email with" + id);

        JobOfferNotification data = jobOfferEmailService.producerSendNotification(id);

        return BuildResponse.success(data,
                "Email is sent successfully ",
                request.getRequestURI());

    }

}


