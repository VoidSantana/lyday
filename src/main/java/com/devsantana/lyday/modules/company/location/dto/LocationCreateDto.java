package com.devsantana.lyday.modules.company.location.dto;

import lombok.Data;

@Data
public class LocationCreateDto {

    private String street;
    private String level;
    private String position;

    private Long warehouseId;
}
