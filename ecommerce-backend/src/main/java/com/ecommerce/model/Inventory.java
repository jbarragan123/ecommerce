package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToOne
    private Product product;

    private int quantity;
}