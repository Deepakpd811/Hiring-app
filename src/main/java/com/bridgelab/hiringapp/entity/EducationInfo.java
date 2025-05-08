package com.bridgelab.hiringapp.entity;


import com.bridgelab.hiringapp.dto.EducationInfoDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "education_info")
public class EducationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "educationInfo")
    private Candidate candidate;

    @Column(nullable = false, length = 30)
    private String degree;

    @Column(nullable = false, length = 30)
    private String institution;

    @Column(nullable = false, length = 30)
    private int yearOfPassing;

    public EducationInfo(EducationInfoDto dto){
        this.institution = dto.getInstitution();;
        this.yearOfPassing = dto.getYearOfPassing();
        this.degree = dto.getDegree();
    }


}
