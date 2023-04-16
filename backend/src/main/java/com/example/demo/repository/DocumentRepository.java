package com.example.demo.repository;

import com.example.demo.model.Company;
import com.example.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
