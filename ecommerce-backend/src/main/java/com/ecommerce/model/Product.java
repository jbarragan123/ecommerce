package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;

    private String name;
    private String description;
    private double price;
    private boolean active = true;
}