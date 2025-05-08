package com.bridgelab.hiringapp.service;


import com.bridgelab.hiringapp.dto.BankInfoDto;
import com.bridgelab.hiringapp.entity.BankInfo;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.repository.BankInfoRepository;
import com.bridgelab.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankInfoService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    BankInfoRepository bankInfoRepository;

    public BankInfo postPersonalInfoAndUpdateCandidate(Long id, String path , BankInfoDto dto ){

        Candidate candidate = candidateRepository.findById(id).orElseThrow(()-> new CandidateNotFoundException(id, path+id ));

        BankInfo bankInfo = candidate.getBankInfo();

        if(bankInfo==null){
            bankInfo = new BankInfo(dto);
            bankInfo.setCandidate(candidate); // set candidate in bankinfo
        }

        BankInfo saveInfo = bankInfoRepository.save(bankInfo);

        candidate.setBankInfo(saveInfo);         // set bankinfo in candidate
        candidateRepository.save(candidate);

        return saveInfo;
    }
}
