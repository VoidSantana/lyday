package com.devsantana.lyday.modules.company.warehouses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWarehouseDto {

    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotNull
    private Long branchId;
}
