package com.bridgelab.hiringapp.dto;

import com.bridgelab.hiringapp.entity.Candidate;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class OnboardStatusDto {

    @NotBlank(message = "status cant be empty")
    @Enumerated
    Candidate.OnboardStatus onboardStatus;

}
