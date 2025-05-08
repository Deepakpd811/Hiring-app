package com.bridgelab.hiringapp.repository;

import com.bridgelab.hiringapp.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
}
