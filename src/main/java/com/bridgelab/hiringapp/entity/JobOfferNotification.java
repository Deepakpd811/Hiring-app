package com.bridgelab.hiringapp.entity;

import com.bridgelab.hiringapp.dto.JobOfferNotificationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Setter
@Getter
public class JobOfferNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonIgnore
    private Candidate candidate;

    @Column(nullable = false, length = 10)
    private boolean sent;

    @Column(nullable = false, length = 50)
    private int retryCount;

    @Column(nullable = false, length = 100)
    private String message;

    public JobOfferNotification(JobOfferNotificationDTO jobOfferNotificationDTO, Candidate candidate) {
        this.candidate = candidate;
        this.sent = jobOfferNotificationDTO.isSent();
        this.retryCount = jobOfferNotificationDTO.getRetryCount();
        this.message = jobOfferNotificationDTO.getMessage();
    }


}
