package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCourierCompanyIdAndStatus(int courierCompanyId, String status);

    Iterable<Order> findByStatus(String status);

    Order findByRequestId(String requestId);

    List<Order> findByCourierIdAndStatus(Integer courierId, String status);
}
