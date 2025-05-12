package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;

import com.bridgelab.hiringapp.dto.EducationInfoDto;

import com.bridgelab.hiringapp.entity.EducationInfo;
import com.bridgelab.hiringapp.service.EducationInfoService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/api/candidates")
public class EducationInfoController {

    @Autowired
    EducationInfoService educationInfoService;

    @PutMapping("{id}/education-info")
    public ResponseEntity<ApiResponseDto> updateStatusById(HttpServletRequest request,
                                                           @Valid  @RequestBody EducationInfoDto educationInfoDto,
                                                           @PathVariable Long id) {
        EducationInfo data = educationInfoService.
                postPersonalInfoAndUpdateCandidate(id,request.getRequestURI(), educationInfoDto);

        return BuildResponse.success(data,
                "Status is updated successfully",
                request.getRequestURI());
    }

}
