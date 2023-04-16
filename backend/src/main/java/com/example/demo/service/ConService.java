package com.example.demo.service;

import com.example.demo.Util.EgovUtil;
import com.example.demo.Util.GeneralUtil;
import com.example.demo.model.Courier;
import com.example.demo.model.Order;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class ConService {
    private final CourierRepository courierRepository;
    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;
    public Order approveCourier(Integer orderId, Integer courierId) {
        Order order = orderRepository.findById(orderId).get();
        Courier courier = courierRepository.findById(courierId).get();
        order.setCourierCode(GeneralUtil.generateRandomCode());
        if (order.getCourierId() == courierId){
            String message = "Вы пришли в " + order.getCon() + ". Чтобы принять доставку документов на адрес " + order.getAddress() + " . Продиктуйте работнику ЦОН код " + order.getCourierCode();
            EgovUtil.sendSMS(courier.getIin(), message, "");
        }
        return orderRepository.save(order);
    }

    public ResponseEntity<Order> sendOffCourier(Integer orderId, String code){
        Order order = orderRepository.findById(orderId).get();
        String orderCode = order.getCourierCode();
        if (code.equals(orderCode) ){
            order.setStatus("4");
            sendOTPtoClient(order);
            order.setDateTimePickedUp(Timestamp.from(Instant.now()));
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        }
        return new ResponseEntity<>(order, HttpStatus.FORBIDDEN);
    }

    private void sendOTPtoClient(Order order){
        String smsIIN = order.getRepresentative() == null ? order.getCustomerIIN() : order.getRepresentative();
        EgovUtil.sendSMS(smsIIN, "Здравствуйте! Курьеру #"+ order.getCourierId() + " выданы ваши документы по заявке #" +
                order.getRequestId() + ". Как только документы доставят, Продиктуйте курьеру пароль: " + order.getCode(), "");

    }

    public Order sendDocumentsReady(Integer orderId) {
        Order order = orderRepository.findById(orderId).get();
        String url = "http://10.101.59.85:5173/order/" + order.getRequestId();
        String message = "Сіздің #" + order.getRequestId() +" құжатыңыз дайын. \n" + url + " сілтемесін басу арқылы құжатты жеткізуді пайдалана аласыз. Құжатты жеткізу курьерлік қызметтің жеткізу мерзімдеріне сәйкес жүзеге асырылады. Ваш документ #" + order.getId() + " готов. \nМожете воспользоваться доставкой документа следуя по ссылке " + url + ". Доставка осуществляется в соответствии со сроками доставки курьерской службы";
        EgovUtil.sendSMS(order.getCustomerIIN(), message, "");
        return order;
    }

    public Iterable<Order> getDocumentsReady() {
        return orderRepository.findByStatus("0");
    }

    public Iterable<Order> getDocumentsCourierAssigned() {
        return orderRepository.findByStatus("3");
    }
}
