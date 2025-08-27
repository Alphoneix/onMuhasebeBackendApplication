package com.yalcin.alptekin.onmuhasebebackendv2.income;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "income-amount")
    private Double amount;

}
