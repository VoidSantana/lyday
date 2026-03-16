package com.devsantana.lyday.modules.company.location.service;

import com.devsantana.lyday.modules.company.location.dto.LocationCreateDto;
import com.devsantana.lyday.modules.company.location.dto.LocationGenerateDto;
import com.devsantana.lyday.modules.company.location.dto.LocationResponseDto;

import java.util.List;


public interface LocationService {

    LocationResponseDto create(LocationCreateDto dto);

    List<LocationResponseDto> findAll();

    LocationResponseDto findById(Long id);

    List<LocationResponseDto> findByWarehouse(Long warehouseId);

    LocationResponseDto findByCode(String code);

    void generateLocations(LocationGenerateDto dto);
}