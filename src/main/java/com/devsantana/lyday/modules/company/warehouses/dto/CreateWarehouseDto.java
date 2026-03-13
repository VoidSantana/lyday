package com.devsantana.lyday.modules.company.warehouses.dto;

import lombok.Data;

@Data
public class CreateWarehouseDto {

    private String name;
    private String code;
    private Long branchId;
}
