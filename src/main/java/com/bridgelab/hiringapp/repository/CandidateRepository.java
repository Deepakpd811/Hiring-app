package com.bridgelab.hiringapp.repository;

import com.bridgelab.hiringapp.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByStatus(Candidate.Status status);
}
