package com.devsantana.lyday.modules.company.warehouses.mapper;


import com.devsantana.lyday.modules.company.branch.model.Branch;
import com.devsantana.lyday.modules.company.warehouses.dto.CreateWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.ResponseWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.model.Warehouse;

public class WarehouseMapper {

    public static Warehouse toEntity(CreateWarehouseDto dto, Branch branch){

        return Warehouse.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .branch(branch)
                .build();
    }
    public static ResponseWarehouseDto toDto(Warehouse warehouse){
        return ResponseWarehouseDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .code(warehouse.getCode())
                .branchId(warehouse.getBranch().getId())
                .build();
    }
}
