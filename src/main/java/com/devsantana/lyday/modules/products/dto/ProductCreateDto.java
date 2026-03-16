package com.devsantana.lyday.modules.products.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class ProductCreateDto {

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 150)
    private String name;
    @NotBlank(message = "SKU é obrigatório.")
    @Size(max = 50)
    private String sku;
    @NotNull(message = "Peso é obrigatório.")
    @Positive(message = "Peso deve ser maior que 0.")
    private Double weightKg;
    @NotNull(message = "Volumetria do item é obrigatória.")
    @Positive(message = "Volumetria não pode ser 0.")
    private Integer volumeCm3;
    @Size(max = 500)
    private String description;
    @Size(max = 100)
    private String brand;
    @NotNull(message = "Campo >preço< não pode ficar vazio")
    @Positive(message = "Preço não pode ser >0<")
    private double price;
}
