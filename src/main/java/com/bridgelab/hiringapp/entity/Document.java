package com.bridgelab.hiringapp.entity;

import com.bridgelab.hiringapp.dto.DocumentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "candidate_documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonIgnore
    private Candidate candidate;

    @Column(nullable = false, length = 50)
    private String documentType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileUrl; // Store URL of the uploaded document

    @Column(nullable = false)
    private boolean verified = false; // default to false


    public Document(DocumentDto dto){
        this.fileUrl = dto.getFileUrl();
        this.documentType = dto.getDocumentType();
        this.verified = dto.isVerified();
    }

}

