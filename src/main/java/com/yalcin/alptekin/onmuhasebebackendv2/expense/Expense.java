package com.yalcin.alptekin.onmuhasebebackendv2.expense;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense-amount")
    private Double amount;
}