package com.devsantana.lyday.modules.company.location.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResponseDto {

    private Long id;
    private String street;
    private String shelf;
    private String level;
    private String position;
    private String code;

    private Long warehouseId;
}
