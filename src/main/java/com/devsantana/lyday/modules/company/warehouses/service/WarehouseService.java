package com.devsantana.lyday.modules.company.warehouses.service;

import com.devsantana.lyday.modules.company.warehouses.dto.CreateWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.ResponseWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.UpdateWarehouseDto;

import java.util.List;

public interface WarehouseService {

    ResponseWarehouseDto create(CreateWarehouseDto dto);

    ResponseWarehouseDto findById(Long id);

    List<ResponseWarehouseDto> findAll();

    List<ResponseWarehouseDto> findByBranchId(Long branchId);

    ResponseWarehouseDto update(Long id, UpdateWarehouseDto dto);

    void delete(Long id);
}
