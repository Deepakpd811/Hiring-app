package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.*;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.service.CandidateService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ResponseEntity<ApiResponseDto> getAllCandidates(HttpServletRequest request) {
        List<Candidate> candidates = candidateService.getCandidateData();
        return BuildResponse.success(candidates, "List of all candidates", request.getRequestURI());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getCandidateById(@PathVariable Long id, HttpServletRequest request) {
        Candidate candidate = candidateService.getCandidateDataById(id);
        return BuildResponse.success(candidate, "Candidate by ID", request.getRequestURI());
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponseDto> getCandidateCount(HttpServletRequest request) {
        int count = candidateService.getTotalCount();
        return BuildResponse.success(count, "Total candidate count", request.getRequestURI());
    }

    @GetMapping("/hired")
    public ResponseEntity<ApiResponseDto> getHiredCandidates(HttpServletRequest request) {
        List<Candidate> hiredCandidates = candidateService.hiredCandidate();
        return BuildResponse.success(hiredCandidates, "List of hired candidates", request.getRequestURI());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createCandidate(@RequestBody CandidateDto candidateDto, HttpServletRequest request) {
        Candidate createdCandidate = candidateService.createCandidateData(candidateDto);
        return BuildResponse.success(createdCandidate, "Candidate created successfully", request.getRequestURI());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto> updateCandidateStatus(@PathVariable Long id,
                                                                @RequestBody StatusUpdateDto statusDto,
                                                                HttpServletRequest request) {
        Candidate updatedCandidate = candidateService.updateStatusByid(id, request.getRequestURI(), statusDto);
        return BuildResponse.success(updatedCandidate, "Candidate status updated successfully", request.getRequestURI());
    }

    @PutMapping("/{id}/onboard-status")
    public ResponseEntity<ApiResponseDto> updateCandidateOnboardStatus(@PathVariable Long id,
                                                                       @RequestBody OnboardStatusDto onboardStatusDto,
                                                                       HttpServletRequest request) {
        Candidate updatedCandidate = candidateService.updateOnboardStatusByid(id, request.getRequestURI(), onboardStatusDto);
        return BuildResponse.success(updatedCandidate, "Candidate onboard status updated successfully", request.getRequestURI());
    }
}
