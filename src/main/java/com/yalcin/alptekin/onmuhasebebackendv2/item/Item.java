package com.yalcin.alptekin.onmuhasebebackendv2.item;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "duration_unit") // "MONTH", "YEAR"
    private String durationUnit;

    @Column(name = "duration_value")
    private Integer durationValue;

    @Column(name = "payment_frequency_unit") // "ONCE", "MONTH", "YEAR"
    private String paymentFrequencyUnit;

    @Column(name = "payment_frequency_value")
    private Integer paymentFrequencyValue;

    @Column(name = "total_amount")
    private Double totalAmount;
}