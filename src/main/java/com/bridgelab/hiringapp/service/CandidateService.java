package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.CandidateDto;
import com.bridgelab.hiringapp.dto.OnboardStatusDto;
import com.bridgelab.hiringapp.dto.StatusUpdateDto;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.exception.EmailAlreadyExistException;
import com.bridgelab.hiringapp.exception.ResourceNotFoundException;
import com.bridgelab.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    public Page<Candidate> getCandidateData(Pageable pageable) {
       Page<Candidate>  candidates = candidateRepository.findAll(pageable);

       if(candidates.isEmpty()){
           throw new ResourceNotFoundException("No candidate data found");
       }
       return candidates;
    }


    public Candidate getCandidateDataById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id, "api/candidate/" + id));
    }


    public Candidate createCandidateData(CandidateDto dto) {

        if (candidateRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistException("Candidate with this email already exists");
        }

        Candidate c1 = new Candidate(dto);
        System.out.println(c1);
        return candidateRepository.save(c1);
    }

    public int getTotalCount() {
        return (int) candidateRepository.count();
    }

    public List<Candidate> hiredCandidate() {
        return candidateRepository.findByStatus(Candidate.Status.ONBOARDED);
    }


    // delete candidate by id
    public void deleteCandidateData(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id, "api/candidates/delete" + id));

        candidateRepository.delete(candidate);
    }

    // update status
    public Candidate updateStatusByid(Long id, String path, StatusUpdateDto sdto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id, path));

        candidate.setStatus(sdto.getStatus());
        return candidateRepository.save(candidate);
    }

    // update onboard status
    public Candidate updateOnboardStatusByid(Long id, String path, OnboardStatusDto sdto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id, path));

        candidate.setOnboardStatus(sdto.getOnboardStatus());
        return candidateRepository.save(candidate);
    }


}
