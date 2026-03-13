package com.devsantana.lyday.modules.company.warehouses.controller;

import com.devsantana.lyday.modules.company.warehouses.dto.CreateWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.ResponseWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.UpdateWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseWarehouseDto create(@RequestBody CreateWarehouseDto dto){
        return warehouseService.create(dto);
    }
    @GetMapping
    public List<ResponseWarehouseDto> findAll(){
        return warehouseService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseWarehouseDto findById(@PathVariable Long id){
        return warehouseService.findById(id);
    }
    @GetMapping("/branch/{branchId}")
    public List<ResponseWarehouseDto> findByBranchId(@PathVariable Long branchId){
        return warehouseService.findByBranchId(branchId);
    }
    @PutMapping("/{id}")
    public ResponseWarehouseDto update(@PathVariable Long id, @RequestBody UpdateWarehouseDto dto){
        return warehouseService.update(id, dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        warehouseService.delete(id);
    }
}
