package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.service.DocumentService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/candidates")
public class DocumentsController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(path = "{id}/upload-document", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto> uploadDocument(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam("documentType") String documentType,
            @RequestParam("file") MultipartFile file) {

        String url = documentService.saveDocument(id, request.getRequestURI(), documentType, file);

        return BuildResponse.success(url,"Status is updated successfully",request.getRequestURI());
    }

//    @PutMapping("/{id}/download-document")
//    public ResponseEntity<ApiResponseDto> downloadDocument(){
//
//    }

}
