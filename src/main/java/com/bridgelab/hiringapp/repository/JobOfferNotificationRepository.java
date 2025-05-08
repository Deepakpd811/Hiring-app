package com.bridgelab.hiringapp.repository;

import com.bridgelab.hiringapp.entity.JobOfferNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferNotificationRepository extends JpaRepository<JobOfferNotification, Long> {
}
