package com.morebite.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orderItem")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double amount;
    private int quantity;
}
