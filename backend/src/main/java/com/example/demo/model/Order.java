package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.annotation.sql.DataSourceDefinitions;
import javax.persistence.*;

import static com.example.demo.Util.GeneralUtil.generateRandomCode;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "Request_Id", nullable = false)
    private String requestId;

    @Column(name = "Customer_IIN", nullable = false)
    private String customerIIN;

    @Column(name="Service_Type", nullable = false)
    private String serviceType;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Courier_Company_ID", nullable = false)
    private int courierCompanyId;

    @Column(name = "Courier_ID")
    private int courierId;

    @Column(name = "Address")
    private String address;

    @Column(name = "Code")
    private String code;

    @Column(name = "CourierCode")
    private String courierCode;

    @Column(name = "CON", nullable = false)
    private String con;

    @Column(name="DateTime_Ready")
    private Date dateTimeReady;

    @Column(name = "DateTime_Ordered")
    private Date dateTimeOrdered;

    @Column(name = "DateTime_PickedUp")
    private Date dateTimePickedUp;

    @Column(name = "DateTime_Handled")
    private Date dateTimeHandled;

    @Column(name = "Representative")
    private String representative;

    @Column(name="Cost")
    private Integer cost;

    @Column(name="Distance")
    private Integer distance;

    public Order(String requestId, String customerIIN, String status, String address, String serviceType){
        this.requestId = requestId;
        this.customerIIN = customerIIN;
        this.status = status;
        this.address = address;
        this.con = "ЦОН Керей Жанибек 4/3";
        this.dateTimeReady = Timestamp.from(Instant.now());
        this.serviceType = serviceType;
    }

    public Order(String requestId, String customerIIN, String status, String address, int courierCompanyId, String serviceType){
        this.requestId = requestId;
        this.customerIIN = customerIIN;
        this.status = status;
        this.address = address;
        this.con = "ЦОН Керей Жанибек 4/3";
        this.courierCompanyId = courierCompanyId;
        this.dateTimeReady = Timestamp.from(Instant.now());
        this.code = generateRandomCode();
        this.serviceType = serviceType;
    }

    public Order(String requestId, String customerIIN, String serviceType) {
        this.requestId = requestId;
        this.customerIIN = customerIIN;
        this.status = "0";
        this.con = "ЦОН Керей Жанибек 4/3";
        this.serviceType = serviceType;
        this.dateTimeReady = Timestamp.from(Instant.now());
    }
}
