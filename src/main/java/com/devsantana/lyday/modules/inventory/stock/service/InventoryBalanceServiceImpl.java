package com.devsantana.lyday.modules.inventory.stock.service;

import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.company.location.repository.LocationRepository;
import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceCreateDto;
import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceResponseDto;
import com.devsantana.lyday.modules.inventory.stock.mapper.InventoryBalanceMapper;
import com.devsantana.lyday.modules.inventory.stock.model.InventoryBalance;
import com.devsantana.lyday.modules.inventory.stock.repository.InventoryBalanceRepository;
import com.devsantana.lyday.modules.products.model.Product;
import com.devsantana.lyday.modules.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryBalanceServiceImpl implements InventoryBalanceService{

    private final InventoryBalanceRepository inventoryBalanceRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    @Override
    public InventoryBalanceResponseDto create(InventoryBalanceCreateDto dto){

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(()->new EntityNotFoundException("Produto não encontrado"));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(()->new EntityNotFoundException("Locação não encontrada"));
        InventoryBalance inventory = inventoryBalanceRepository
                .findByProductIdAndLocationId(dto.getProductId(), dto.getLocationId())
                .orElse(null);
        if (inventory == null){
            inventory = InventoryBalanceMapper.toEntity(dto, product, location);
        } else {
            inventory.setQuantity(
                    inventory.getQuantity() + dto.getQuantity()
            );
        }
        InventoryBalance saved = inventoryBalanceRepository.save(inventory);

        return InventoryBalanceMapper.toDto(saved);
    }
    @Override
    public InventoryBalanceResponseDto findByProductIdAndLocationId(Long productId, Long locationId){

        InventoryBalance inventory = inventoryBalanceRepository
                .findByProductIdAndLocationId(productId, locationId)
                .orElseThrow(()->new RuntimeException("Inventory não encontrada"));
        return  InventoryBalanceMapper.toDto(inventory);
    }
    @Override
    public List<InventoryBalanceResponseDto> findByProduct(Long productId){
        return inventoryBalanceRepository.findByProductId(productId)
                .stream()
                .map(InventoryBalanceMapper::toDto)
                .toList();
    }
    @Override
    public List<InventoryBalanceResponseDto> findByLocation(Long locationId){
        return inventoryBalanceRepository.findByLocationId(locationId)
                .stream()
                .map(InventoryBalanceMapper::toDto)
                .toList();
    }
}
