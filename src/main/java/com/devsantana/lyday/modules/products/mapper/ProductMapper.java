package com.devsantana.lyday.modules.products.mapper;

import com.devsantana.lyday.modules.products.dto.ProductResponseDto;
import com.devsantana.lyday.modules.products.dto.ProductUpdateDto;
import com.devsantana.lyday.modules.products.dto.ProductCreateDto;
import com.devsantana.lyday.modules.products.model.Product;

public class ProductMapper {

    private ProductMapper(){
        // evita instanciação
    }
    // ==================================
    //  CREATE DTO -> ENTITY
    // ==================================
    public static Product toEntity(ProductCreateDto dto){
        return Product.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .weightKg(dto.getWeightKg())
                .volumeCm3(dto.getVolumeCm3())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .build();

    }
    // ==================================
    // UPDATE DTO -> ENTITY (merge)
    // ==================================
    public static void updateEntity(Product product, ProductUpdateDto dto){
        product.setName(dto.getName());
        product.setWeightKg(dto.getWeightKg());
        product.setVolumeCm3(dto.getVolumeCm3());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
    }
    // ==================================
    // ENTITY -> RESPONSE DTO
    // ==================================
    public static ProductResponseDto toResponse(Product entity){
        return ProductResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sku(entity.getSku())
                .weightKg(entity.getWeightKg())
                .volumeCm3(entity.getVolumeCm3())
                .description(entity.getDescription())
                .brand(entity.getBrand())
                .price(entity.getPrice())
                .build();
    }
}
