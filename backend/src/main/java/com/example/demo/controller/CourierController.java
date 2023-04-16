package com.example.demo.controller;


import com.example.demo.model.Company;
import com.example.demo.model.Courier;
import com.example.demo.model.Order;
import com.example.demo.repository.CourierRepository;
import com.example.demo.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/couriers")
@AllArgsConstructor
public class CourierController {
    private final CourierRepository courierRepository;
    private final CourierService courierService;

    @GetMapping
    public Iterable<Courier> getAll(){
        return courierRepository.findAll();
    }

    @PostMapping("/new")
    public Courier addCourier(@RequestBody Courier courier){
        return courierRepository.save(courier);
    }

    @GetMapping("/{iin}")
    public ResponseEntity<Courier> getById(@PathVariable String iin){
        return courierService.findByIin(iin);
    }


    @GetMapping("/{id}/getAvailableOrders")
    public Iterable<Order> getAvailableOrders(@PathVariable Integer id){
        return courierService.getAvailableOrders(id);
    }

    @GetMapping("/{id}/getCompletedOrders")
    public Iterable<Order> getCompletedOrders(@PathVariable Integer id){
        return courierService.getCompletedOrders(id);
    }

    @GetMapping("/{id}/getMyOrders")
    public Iterable<Order> getMyOrders(@PathVariable Integer id){
        return courierService.getMyOrders(id);
    }

    @PutMapping("/{id}/claimOrder/{orderId}")
    public Order claimOrder(@PathVariable Integer id, @PathVariable Integer orderId){
        return courierService.claimOrder(id, orderId);
    }

    @PutMapping("/{id}/handOrder/{requestId}/{code}")
    public ResponseEntity<Order> handOrder(@PathVariable Integer id, @PathVariable String requestId, @PathVariable String code){
        return courierService.handOrder(id, requestId, code);
    }



}
