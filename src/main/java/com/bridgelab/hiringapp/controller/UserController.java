package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.dto.CandidateDto;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.service.CandidateService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api/candidates")
public class UserController {

    @Autowired
    CandidateService candidateService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createCandidate(@Valid  @RequestBody CandidateDto candidateDto, HttpServletRequest request) {
        Candidate createdCandidate = candidateService.createCandidateData(candidateDto);
        return BuildResponse.success(createdCandidate, "Candidate created successfully", request.getRequestURI());
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto> getAllCandidates(HttpServletRequest request,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        Page<Candidate> candidates = candidateService.getCandidateData(pageable);
        return BuildResponse.success(candidates, "List of all candidates", request.getRequestURI());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getCandidateById(@PathVariable Long id, HttpServletRequest request) {
        Candidate candidate = candidateService.getCandidateDataById(id);
        return BuildResponse.success(candidate, "Candidate by ID", request.getRequestURI());
    }

}
