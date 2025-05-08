package com.bridgelab.hiringapp.entity;

import com.bridgelab.hiringapp.dto.PersonalInfoDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "personal_info")
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "personalInfo")
    @JsonBackReference
    private Candidate candidate;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 20)
    private String gender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, length = 50)
    private String nationality;

    public PersonalInfo (PersonalInfoDto dto){
        this.dob = dto.getDob();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.nationality = dto.getNationality();
    }

}
