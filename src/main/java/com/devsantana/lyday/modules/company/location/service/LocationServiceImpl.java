package com.devsantana.lyday.modules.company.location.service;

import com.devsantana.lyday.modules.company.location.Mapper.LocationMapper;
import com.devsantana.lyday.modules.company.location.dto.LocationCreateDto;
import com.devsantana.lyday.modules.company.location.dto.LocationResponseDto;
import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.company.location.repository.LocationRepository;
import com.devsantana.lyday.modules.company.warehouses.model.Warehouse;
import com.devsantana.lyday.modules.company.warehouses.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;



    @Override
    public LocationResponseDto create(LocationCreateDto dto) {

        Warehouse warehouse = warehouseRepository
                .findById(dto.getWarehouseId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Warehouse Not Found"));
        if (locationRepository.existsByWarehouseIdAndStreetAndLevelAndPosition(
                dto.getWarehouseId(),
                dto.getStreet(),
                dto.getLevel(),
                dto.getPosition()
        )){
            throw new RuntimeException("Locação já existe");
        }

        Location location = LocationMapper.toEntity(dto, warehouse);
        Location saved = locationRepository.save(location);

        return LocationMapper.toDto(saved);
    }
    @Override
    public List<LocationResponseDto> findAll(){
        return locationRepository.findAll()
                .stream()
                .map(LocationMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public LocationResponseDto findById(Long id){
        Location location = locationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Location Not Found"));
        return LocationMapper.toDto(location);
    }
    @Override
    public List<LocationResponseDto> findByWarehouse(Long warehouseId){
        return locationRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(LocationMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        Location location = locationRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("Locação não existe"));
        locationRepository.delete(location);
    }
    @Override
    public LocationResponseDto findByCode(String code){
        Location location = locationRepository.findByCode(code)
                .orElseThrow(()-> new RuntimeException("Locação Não Encontrada"));
        return LocationMapper.toDto(location);
    }
}