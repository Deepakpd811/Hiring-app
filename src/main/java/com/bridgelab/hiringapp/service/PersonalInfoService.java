package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.PersonalInfoDto;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.entity.PersonalInfo;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.repository.CandidateRepository;
import com.bridgelab.hiringapp.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    // post user info

    public PersonalInfo postPersonalInfoAndUpdateCandidate(Long id,String path ,PersonalInfoDto dto ){

        Candidate candidate = candidateRepository.findById(id).orElseThrow(()-> new CandidateNotFoundException(id, path+id ));

        PersonalInfo personalInfo = candidate.getPersonalInfo();

        if(personalInfo==null){
            personalInfo = new PersonalInfo(dto);
            personalInfo.setCandidate(candidate);
        }

        PersonalInfo saveInfo = personalInfoRepository.save(personalInfo);

        candidate.setPersonalInfo(saveInfo);
        candidateRepository.save(candidate);

        return saveInfo;
    }




}
