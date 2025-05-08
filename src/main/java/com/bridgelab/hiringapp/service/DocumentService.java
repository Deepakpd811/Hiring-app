package com.bridgelab.hiringapp.service;

import com.bridgelab.hiringapp.dto.DocumentDto;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.entity.Document;
import com.bridgelab.hiringapp.exception.CandidateNotFoundException;
import com.bridgelab.hiringapp.repository.CandidateRepository;
import com.bridgelab.hiringapp.repository.DocumentRepository;
import com.bridgelab.hiringapp.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

        @Autowired
        private DocumentRepository documentRepository;

        @Autowired
        private CandidateRepository candidateRepository;

        public String saveDocument(Long id,  String path, String documentType, MultipartFile file){

            Candidate candidate =candidateRepository.findById(id).orElseThrow(()-> new CandidateNotFoundException(id, path + id));

            String file_url = FileUploadUtil.save(file);

            DocumentDto dto =  DocumentDto.builder()
                    .documentType(documentType)
                    .fileUrl(file_url)
                    .verified(false)
                    .build();

            Document document = new Document(dto);


            document.setCandidate(candidate);

            documentRepository.save(document);

            return file_url;


        }

}
