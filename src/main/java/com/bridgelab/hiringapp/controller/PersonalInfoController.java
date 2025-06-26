package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.dto.PersonalInfoDto;
import com.bridgelab.hiringapp.entity.PersonalInfo;
import com.bridgelab.hiringapp.service.PersonalInfoService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/api/candidates")
public class PersonalInfoController {

    @Autowired
    PersonalInfoService personalInfoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("{id}/personal-info")
    public ResponseEntity<ApiResponseDto> updateStatusById(HttpServletRequest request,
                                                           @Valid @RequestBody PersonalInfoDto personalDto,
                                                           @PathVariable Long id) {
        PersonalInfo data = personalInfoService.
                postPersonalInfoAndUpdateCandidate(id,request.getRequestURI(), personalDto);

        return BuildResponse.success(data,
                "Status is updated successfully",
                request.getRequestURI());
    }


}
