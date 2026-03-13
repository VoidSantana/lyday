package com.devsantana.lyday.modules.company.warehouses.service;

import com.devsantana.lyday.modules.company.branch.model.Branch;
import com.devsantana.lyday.modules.company.branch.repository.BranchRepository;
import com.devsantana.lyday.modules.company.warehouses.dto.CreateWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.ResponseWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.dto.UpdateWarehouseDto;
import com.devsantana.lyday.modules.company.warehouses.mapper.WarehouseMapper;
import com.devsantana.lyday.modules.company.warehouses.model.Warehouse;
import com.devsantana.lyday.modules.company.warehouses.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final BranchRepository branchRepository;

    @Override
    public ResponseWarehouseDto create(CreateWarehouseDto dto) {
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(()-> new RuntimeException("Branch Not Found"));

        Warehouse warehouse = WarehouseMapper.toEntity(dto, branch);

        Warehouse saved = warehouseRepository.save(warehouse);

        return WarehouseMapper.toDto(saved);
    }

    @Override
    public  ResponseWarehouseDto findById(Long id){

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Warehouse Not Found"));

        return WarehouseMapper.toDto(warehouse);
    }
    @Override
    public List<ResponseWarehouseDto> findAll(){
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<ResponseWarehouseDto> findByBranchId(Long branchId){
        return warehouseRepository.findByBranchId(branchId)
                .stream()
                .map(WarehouseMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public ResponseWarehouseDto update(Long id, UpdateWarehouseDto dto){

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Warehouse não encontrada"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(()-> new RuntimeException("Branch não encontrada"));

        warehouse.setName(dto.getName());
        warehouse.setCode(dto.getCode());
        warehouse.setBranch(branch);

        Warehouse updated = warehouseRepository.save(warehouse);

        return WarehouseMapper.toDto(updated);
    }


    @Override
    public void delete(Long id){
        warehouseRepository.deleteById(id);
    }
}
