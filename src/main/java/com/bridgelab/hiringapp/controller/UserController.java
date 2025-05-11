package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.dto.CandidateDto;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.service.CandidateService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/candidate")
public class UserController {

    @Autowired
    CandidateService candidateService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createCandidate(@RequestBody CandidateDto candidateDto, HttpServletRequest request) {
        Candidate createdCandidate = candidateService.createCandidateData(candidateDto);
        return BuildResponse.success(createdCandidate, "Candidate created successfully", request.getRequestURI());
    }
}
