package com.example.demo.controller;


import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping()
    public Iterable<Order> getAll(){
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public Optional<Order> getById(@PathVariable Integer orderId){
        return orderRepository.findById(orderId);
    }

    @GetMapping("/calculate/{companyId}/{address}")
    public Iterable<Integer> calculate(@PathVariable Integer companyId,@PathVariable String address ){
        return orderService.calculate(companyId, address);
    }

    @PutMapping("/createOrder/{requestId}/{companyId}/{address}/{representative}")
    public Order createOrder(@PathVariable String requestId, @PathVariable Integer companyId,@PathVariable String address, @PathVariable String representative ){
        return orderService.createOrder(requestId, companyId, address, representative);
    }

    @GetMapping("/getCost")
    public Integer getCost(@RequestParam String adress1, @RequestParam String adress2){
        return orderService.calculateCost(adress1, adress2);
    }

    @PutMapping("/approvePayment/{requestId}")
    public Order approvePayment(@PathVariable String requestId){
        return orderService.approvePayment(requestId);
    }



}
