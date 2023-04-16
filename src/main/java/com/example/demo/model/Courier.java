package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Couriers")
@AllArgsConstructor
@NoArgsConstructor
public class Courier {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "IIN", nullable = false)
    private String iin;

    @Column(name = "Company_ID", nullable = false)
    private int companyId;

    public Courier(String iin, int companyId){
        this.iin = iin;
        this.companyId = companyId;
    }
}

