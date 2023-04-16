package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Documents")
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Order_ID", nullable = false)
    private int orderId;

}
