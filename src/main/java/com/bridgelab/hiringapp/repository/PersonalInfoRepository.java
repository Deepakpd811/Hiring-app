package com.bridgelab.hiringapp.repository;

import com.bridgelab.hiringapp.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
}
