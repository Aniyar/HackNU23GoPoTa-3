package com.example.demo.controller;


import com.example.demo.Util.EgovUtil;
import com.example.demo.Util.GeneralUtil;
import com.example.demo.model.Courier;
import com.example.demo.model.Order;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.ConService;
import com.example.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/con")
@AllArgsConstructor
public class ConController {
    private final CourierRepository courierRepository;
    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;
    private final ConService conService;

    @PutMapping("/approve/{orderId}/{courierId}")
    public Order approveCourier(@PathVariable Integer orderId, @PathVariable Integer courierId){
        return conService.approveCourier(orderId, courierId);
    }

    @PutMapping("/sendOffCourier/{orderId}/{code}")
    public ResponseEntity<Order> sendOffCourier(@PathVariable Integer orderId, @PathVariable String code){
        return conService.sendOffCourier(orderId, code);
    }

    @PostMapping("/sendDocumentsReady/{orderId}")
    public Order sendDocumentsReady(@PathVariable Integer orderId){
        return conService.sendDocumentsReady(orderId);
    }

    @GetMapping("/getDocumentsReady")
    public Iterable<Order> getDocumentsReady(){
        return conService.getDocumentsReady();
    }

    @GetMapping("/getDocumentsCourierAssigned")
    public Iterable<Order> getDocumentsCourierAssigned(){
        return conService.getDocumentsCourierAssigned();
    }
}
