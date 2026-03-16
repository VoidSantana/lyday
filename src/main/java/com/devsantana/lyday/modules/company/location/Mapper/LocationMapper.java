package com.devsantana.lyday.modules.company.location.Mapper;

import com.devsantana.lyday.modules.company.location.dto.LocationCreateDto;
import com.devsantana.lyday.modules.company.location.dto.LocationResponseDto;
import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.company.warehouses.model.Warehouse;

public class LocationMapper {

    public static Location toEntity(LocationCreateDto dto, Warehouse warehouse){
        String code = dto.getStreet()
                + "-" + dto.getShelf()
                + "-" + dto.getLevel()
                + "-" + dto.getPosition();
        return Location.builder()
                .street(dto.getStreet())
                .shelf(dto.getShelf())
                .level(dto.getLevel())
                .position(dto.getPosition())
                .code(code)
                .warehouse(warehouse)
                .build();
    }
    public static LocationResponseDto toDto(Location location){
        return LocationResponseDto.builder()
                .id(location.getId())
                .street(location.getStreet())
                .shelf(location.getShelf())
                .level(location.getLevel())
                .position(location.getPosition())
                .code(location.getCode())
                .warehouseId(location.getWarehouse().getId())
                .build();
    }
}
