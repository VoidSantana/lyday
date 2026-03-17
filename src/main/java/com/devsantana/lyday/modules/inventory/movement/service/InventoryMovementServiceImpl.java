package com.devsantana.lyday.modules.inventory.movement.service;

import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.company.location.repository.LocationRepository;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementCreateDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementFilterDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementResponseDto;
import com.devsantana.lyday.modules.inventory.movement.mapper.InventoryMovementMapper;
import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovement;
import com.devsantana.lyday.modules.inventory.movement.repository.InventoryMovementRepository;
import com.devsantana.lyday.modules.inventory.movement.repository.InventoryMovementSpecification;
import com.devsantana.lyday.modules.inventory.stock.model.InventoryBalance;
import com.devsantana.lyday.modules.inventory.stock.repository.InventoryBalanceRepository;
import com.devsantana.lyday.modules.products.model.Product;
import com.devsantana.lyday.modules.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryMovementServiceImpl implements InventoryMovementService{

    private final InventoryMovementRepository movementRepository;
    private final InventoryBalanceRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    @Override
    public InventoryMovementResponseDto createMovement(InventoryMovementCreateDto dto){

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(()->new EntityNotFoundException("Produto Não Encontrado"));

        Location source = null;
        Location destination = null;

        if (dto.getSourceLocationId() != null){
            source = locationRepository.findById(dto.getSourceLocationId())
                    .orElseThrow(()-> new EntityNotFoundException("Locação não encontrada (Origem)"));
        }
        if (dto.getDestinationLocationId()!=null){
            destination = locationRepository.findById(dto.getDestinationLocationId())
                    .orElseThrow(()->new EntityNotFoundException("Locação não encontrada (Destino)"));
        }

        InventoryMovement movement =
                InventoryMovementMapper.toEntity(dto,product, source, destination);

        switch (dto.getType()){
            case RECEIVING -> addStock(product, destination, dto.getQuantity());
            case PICKING -> removeStock(product, source, dto.getQuantity());
            case TRANSFER -> {
                removeStock(product, source, dto.getQuantity());
                addStock(product, destination, dto.getQuantity());
            }
            case ADJUSTMENT -> adjustStock(product, destination, dto.getQuantity());
        }
        InventoryMovement saved = movementRepository.save(movement);
        return InventoryMovementMapper.toDto(saved);
    }
    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementResponseDto> findByProduct(Long productId){
        return movementRepository
                .findByProductId(productId)
                .stream()
                .map(InventoryMovementMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public Page<InventoryMovementResponseDto> search(
            InventoryMovementFilterDto filter,
            Pageable pageable
    ){
        Specification<InventoryMovement> spec =
                InventoryMovementSpecification.filter(filter);
        return movementRepository
                .findAll(spec, pageable)
                .map(InventoryMovementMapper::toDto);
    }

    private void addStock(Product product, Location location, Integer quantity){

        InventoryBalance inventory =
                inventoryRepository
                        .findByProductIdAndLocationId(
                                product.getId(),
                                location.getId()
                        )
                        .orElse(null);
        if (inventory == null){
            inventory = InventoryBalance.builder()
                    .product(product)
                    .location(location)
                    .quantity(quantity)
                    .build();
        }else {
            inventory.setQuantity(
                    inventory.getQuantity() + quantity
            );
        }
        inventoryRepository.save(inventory);
    }
    private void removeStock(Product product, Location location, Integer quantity){
        InventoryBalance inventory =
                inventoryRepository
                        .findByProductIdAndLocationId(
                                product.getId(),
                                location.getId()
                        )
                        .orElseThrow(()->new RuntimeException("Estoque não encontrado"));
        if (inventory.getQuantity() < quantity){
            throw new RuntimeException("Estoque Insuficiente");
        }
        inventory.setQuantity(
                inventory.getQuantity() - quantity
        );
        inventoryRepository.save(inventory);
    }
    private void adjustStock(Product product, Location location, Integer quantity){

        InventoryBalance inventory =
                inventoryRepository
                        .findByProductIdAndLocationId(
                                product.getId(),
                                location.getId()
                        )
                        .orElseThrow(()-> new RuntimeException("Estoque não encontrado"));
        inventory.setQuantity(
                inventory.getQuantity() + quantity
        );
        inventoryRepository.save(inventory);
    }
}
