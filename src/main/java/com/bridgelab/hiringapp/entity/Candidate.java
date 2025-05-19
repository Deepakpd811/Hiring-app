package com.bridgelab.hiringapp.entity;

import com.bridgelab.hiringapp.dto.CandidateDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "documents")
@Entity
@NoArgsConstructor
@Table(name = "candidate")
public class Candidate {


    public enum Status {
        APPLIED, INTERVIEWED, OFFERED, ONBOARDED, REJECTED
    }

    public enum OnboardStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.APPLIED;

    @Enumerated(EnumType.STRING)
    @Column(name = "onboard_status", nullable = false)
    private OnboardStatus onboardStatus = OnboardStatus.NOT_STARTED;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "candidate" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Document> documents;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<JobOfferNotification> jobOfferNotifications;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personalInfo_id")
    private PersonalInfo personalInfo;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankinfo_id")
    private BankInfo bankInfo;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id")
    private EducationInfo educationInfo;

    public Candidate(CandidateDto dto){
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.status = getStatus();
        this.phoneNumber = dto.getPhoneNumber();
        this.onboardStatus = dto.getOnboardStatus();
        this.email = dto.getEmail();
    }

}
