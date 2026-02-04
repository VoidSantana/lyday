package com.devsantana.lyday.modules.products.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private String sku;
    private Integer stock;
    private Double weightKg;
    private Integer volumeCm3;
    private String description;
    private String brand;
    private double price;
}
