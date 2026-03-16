package com.devsantana.lyday.modules.products.dto;

import lombok.Data;

@Data
public class ProductUpdateDto {

    private String name;
    private Double weightKg;
    private Integer volumeCm3;
    private String description;
    private String brand;
    private double price;
}
