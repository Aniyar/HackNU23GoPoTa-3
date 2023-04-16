package com.example.demo.service;

import com.example.demo.Util.EgovUtil;
import com.example.demo.Util.GeneralUtil;
import com.example.demo.Util.GoogleMapsUtil;
import com.example.demo.model.Order;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final CourierService courierService;
    private final CourierRepository courierRepository;



    public List<Integer> calculate(Integer courierCompanyId, String address){
        Integer distance = calculateCost("Керей-Жанибек хандар, 4/1, Астана", address);
        Integer costPerKm = companyRepository.findById(courierCompanyId).get().getCost();
        Integer cost = distance * costPerKm;
        return List.of(cost, distance);

    }
    public static Integer calculateCost(String address1, String address2) {
        Integer cost = -1;
        try{
            cost = GoogleMapsUtil.getDrivingDistance(address1, address2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cost;
    }

    public Order createOrder(String requestId, Integer courierCompanyId, String address, String repr){
        Order order = orderRepository.findByRequestId(requestId);
        order.setStatus("1");
        order.setCourierCompanyId(courierCompanyId);
        order.setAddress(address);
        order.setCode(GeneralUtil.generateRandomCode());
        Integer costPerKm = companyRepository.findById(courierCompanyId).get().getCost();
        Integer distance = calculateCost("Керей-Жанибек хандар, 4/1, Астана", address);
        Integer cost = distance * costPerKm;
        order.setCost(cost);
        order.setDistance(distance);
        order.setDateTimeOrdered(Timestamp.from(Instant.now()));
        order.setRepresentative(repr);
        return orderRepository.save(order);
    }


    public Order approvePayment(String requestId) {
        Order order = orderRepository.findByRequestId(requestId);
        order.setStatus("2");
        courierService.sendOutSMS(order);
        return orderRepository.save(order);
    }

}
