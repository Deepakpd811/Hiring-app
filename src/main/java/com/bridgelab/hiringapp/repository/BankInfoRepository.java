package com.bridgelab.hiringapp.repository;

import com.bridgelab.hiringapp.entity.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
}
