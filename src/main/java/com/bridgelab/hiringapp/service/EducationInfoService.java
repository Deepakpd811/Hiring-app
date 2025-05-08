package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.BankInfoDto;
import com.bridgelab.hiringapp.dto.EducationInfoDto;
import com.bridgelab.hiringapp.entity.BankInfo;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.entity.EducationInfo;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.repository.BankInfoRepository;
import com.bridgelab.hiringapp.repository.CandidateRepository;
import com.bridgelab.hiringapp.repository.EducationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationInfoService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    EducationInfoRepository educationInfoRepository;

    public EducationInfo postPersonalInfoAndUpdateCandidate(Long id, String path , EducationInfoDto dto ){

        Candidate candidate = candidateRepository.findById(id).orElseThrow(()-> new CandidateNotFoundException(id, path+id ));

        EducationInfo educationInfo = candidate.getEducationInfo();

        if(educationInfo==null){
            educationInfo = new EducationInfo(dto);
            educationInfo.setCandidate(candidate); // set candidate in EducationInfo
        }

        EducationInfo saveInfo = educationInfoRepository.save(educationInfo);

        candidate.setEducationInfo(saveInfo);        // set EducationInfo in candidate
        candidateRepository.save(candidate);

        return saveInfo;
    }
}
