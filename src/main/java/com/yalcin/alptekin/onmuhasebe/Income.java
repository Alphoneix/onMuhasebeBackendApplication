package com.yalcin.alptekin.onmuhasebe;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "income_value")
    private Double value;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Double getValue() {return value;}
    public void setValue(Double value) {this.value = value;}
}
