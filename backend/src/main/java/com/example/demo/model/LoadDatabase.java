package com.example.demo.model;

import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class LoadDatabase {
    private final CompanyRepository companyRepository;
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    //private final

    @PostConstruct
    private void initDatabase() {
        companyRepository.deleteAll();
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(List.of(
                    new Company(1, "Kaspi", 100),
                    new Company(2, "PonyExpress", 150),
                    new Company(3, "DHL", 200)));
        }
        courierRepository.deleteAll();
        if (courierRepository.count() == 0) {
            courierRepository.saveAll(List.of(
                    new Courier("000821550825", 1),
                    new Courier("000525501114", 1),
                    new Courier("030727651379", 2),
                    new Courier("021013650266", 3)
            ));
        }
//        002241056742,830730300232

        orderRepository.deleteAll();
        if (orderRepository.count() == 0){
            orderRepository.saveAll(List.of(
                    new Order("002241054097", "860904350504", "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241054098", "030727651379", "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241054795", "930823300880", "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241054954", "900319450997", "1", "Алматы 10, Астана", "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241055082", "950905451464", "1", "Кабанбай Батыр 22, Астана", "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241055257", "000430000049", "2", "Орынбор 22, Астана", 1, "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241055387", "921123351335", "2", "Республика 22, Астана", 1, "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241055659", "960217351422", "2", "Абая 22, Астана", 1, "Выдача справки о наличии либо отсутствии судимости"),
                    new Order("002241055886", "860729351086", "2", "Момышулы 22, Астана", 2, "Выдача справки о наличии либо отсутствии судимости")

            ));
        }
    }



}
