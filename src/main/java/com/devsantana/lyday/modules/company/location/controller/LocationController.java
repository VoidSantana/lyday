package com.devsantana.lyday.modules.company.location.controller;

import com.devsantana.lyday.modules.company.location.dto.LocationCreateDto;
import com.devsantana.lyday.modules.company.location.dto.LocationGenerateDto;
import com.devsantana.lyday.modules.company.location.dto.LocationResponseDto;
import com.devsantana.lyday.modules.company.location.service.LocationService;
import com.devsantana.lyday.modules.company.warehouses.dto.ResponseWarehouseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public LocationResponseDto create(@RequestBody LocationCreateDto dto){
        return locationService.create(dto);
    }
    @GetMapping
    public List<LocationResponseDto> findAll(){
        return locationService.findAll();
    }
    @GetMapping("/{id}")
    public LocationResponseDto findById(@PathVariable Long id){
        return locationService.findById(id);
    }
    @GetMapping("/warehouse/{warehouseId}")
    public List<LocationResponseDto> findByWarehouse(@PathVariable Long warehouseId){
        return locationService.findByWarehouse(warehouseId);
    }
    @GetMapping("/code/{code}")
    public LocationResponseDto findByCode(@PathVariable String code){
        return locationService.findByCode(code);
    }
    @PostMapping("/generate")
    public void generate(@RequestBody LocationGenerateDto dto){
        locationService.generateLocations(dto);
    }
}
