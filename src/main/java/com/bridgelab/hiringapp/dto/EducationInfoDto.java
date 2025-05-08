package com.bridgelab.hiringapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfoDto {

    @NotBlank(message = "Degree is required")
    @Size(max = 30, message = "Degree must be at most 30 characters")
    private String degree;

    @NotBlank(message = "Institution is required")
    @Size(max = 30, message = "Institution name must be at most 30 characters")
    private String institution;

    @Min(value = 1900, message = "Year of passing should be a valid year")
    private int yearOfPassing;



}
