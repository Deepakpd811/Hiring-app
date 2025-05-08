package com.bridgelab.hiringapp.entity;

import com.bridgelab.hiringapp.dto.BankInfoDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "bank_info")
public class BankInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String bankName;

    @Column(nullable = false, length = 50)
    private String accountNumber;

    @Column(nullable = false, length = 20)
    private String ifscCode;

    @JsonBackReference
    @OneToOne(mappedBy = "bankInfo") // foreign key
    private Candidate candidate;

    public BankInfo(BankInfoDto dto){
        this.bankName = dto.getBankName();
        this.accountNumber = dto.getAccountNumber();
        this.ifscCode = dto.getIfscCode();
    }


}
