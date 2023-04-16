package com.example.demo.service;

import com.example.demo.Util.EgovUtil;
import com.example.demo.model.Company;
import com.example.demo.model.Courier;
import com.example.demo.model.Order;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;

    public Integer sendOutSMS(Order order){
        Integer companyID = order.getCourierCompanyId();
        List<Courier> couriers = courierRepository.findByCompanyId(companyID);
        String token = EgovUtil.getAccessToken();
        for (int i=0; i<couriers.size(); i++){
            String url = "http://10.101.7.135:8081/couriers/" +couriers.get(i).getId() + "/claimOrder/{orderId}/"+ order.getId() ;
            String message = "Здравствуйте! Вы можете доставить документы из ЦОН-а по адресу Керей Жанибек Хандар 4/3 на адрес " +
                    order.getAddress() + " \nЧтобы закрепить за собой заказ, перейдите по ссылке: " + url;
            EgovUtil.sendSMS(couriers.get(i).getIin(), message, token);
        }
        return couriers.size();
    }


    public Iterable<Order> getAvailableOrders(Integer id) {
        Courier courier = courierRepository.findById(id).get();
        Company company = companyRepository.findById(courier.getCompanyId()).get();
        List<Order> orders = orderRepository.findByCourierCompanyIdAndStatus(company.getId(), "2");
        return orders;
    }

    public Iterable<Order> getMyOrders(Integer id) {
        Iterable<Order> orders = orderRepository.findByStatus("4");
        return orders;
    }

    public Order claimOrder(Integer id, Integer orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setCourierId(id);
        order.setStatus("3");
        return orderRepository.save(order);
    }

    public ResponseEntity<Order> handOrder(Integer id, String requestId, String code){
        Order order = orderRepository.findByRequestId(requestId);
        if (order.getCode().equals(code)){
            order.setStatus("5");
            order.setDateTimeHandled(Timestamp.from(Instant.now()));
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        }
        return new ResponseEntity<>(order, HttpStatus.FORBIDDEN);

    }

    public ResponseEntity<Courier> findByIin(String iin) {
        Optional<Courier> optional = courierRepository.findByIin(iin);
        if (optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Courier(), HttpStatus.NOT_ACCEPTABLE);
    }


    public Iterable<Order> getCompletedOrders(Integer id) {
        return orderRepository.findByStatus("5");
    }
}
