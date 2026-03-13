package com.devsantana.lyday.modules.company.warehouses.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseWarehouseDto {
    
    private Long id;
    private String name;
    private String code;
    private Long branchId;
}
