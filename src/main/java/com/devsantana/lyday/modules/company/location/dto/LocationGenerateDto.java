package com.devsantana.lyday.modules.company.location.dto;

import lombok.Data;

import java.util.List;

@Data
public class LocationGenerateDto {
    private Long warehouseId;

    private int streets;

    private int shelf;

    private List<String> levels;

    private int positions;
}
