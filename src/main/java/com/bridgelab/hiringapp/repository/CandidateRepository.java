package com.bridgelab.hiringapp.repository;

import com.bridgelab.hiringapp.entity.Candidate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByStatus(Candidate.Status status);

    boolean existsByEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") @Size(max = 100, message = "Email cannot exceed 100 characters") String email);

    Page<Candidate> findAll(Pageable pageable);
}
