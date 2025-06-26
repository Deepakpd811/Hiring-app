package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.ApiResponseDto;
import com.bridgelab.hiringapp.dto.BankInfoDto;
import com.bridgelab.hiringapp.entity.BankInfo;
import com.bridgelab.hiringapp.service.BankInfoService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/api/candidates")
public class BankInfoController {

    @Autowired
    BankInfoService bankInfoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("{id}/bank-info")
    public ResponseEntity<ApiResponseDto> updateStatusById(HttpServletRequest request, @Valid @RequestBody BankInfoDto bankInfoDto,
                                                           @PathVariable Long id) {

        BankInfo data = bankInfoService.postPersonalInfoAndUpdateCandidate(id, request.getRequestURI(), bankInfoDto);

        return BuildResponse.success(data, "Status is updated successfully", request.getRequestURI());
    }
}
