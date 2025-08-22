package com.yalcin.alptekin.onmuhasebe;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_amount")
    private Double amount;
}
