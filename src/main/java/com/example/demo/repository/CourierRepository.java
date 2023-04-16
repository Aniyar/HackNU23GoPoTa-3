package com.example.demo.repository;

import com.example.demo.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Integer> {
    List<Courier> findByCompanyId(int companyId);

    Optional<Courier> findByIin(String iin);
}
