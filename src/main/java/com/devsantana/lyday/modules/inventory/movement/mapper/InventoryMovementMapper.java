package com.devsantana.lyday.modules.inventory.movement.mapper;


import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementCreateDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementResponseDto;
import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovement;
import com.devsantana.lyday.modules.products.model.Product;

public class InventoryMovementMapper {

    private InventoryMovementMapper(){}

    public static InventoryMovement toEntity(
            InventoryMovementCreateDto dto,
            Product product,
            Location source,
            Location destination
    ){
        return InventoryMovement.builder()
                .product(product)
                .type(dto.getType())
                .sourceLocation(source)
                .destinationLocation(destination)
                .quantity(dto.getQuantity())
                .reason(dto.getReason())
                .build();
    }
    public static InventoryMovementResponseDto toDto(InventoryMovement entity){
        return InventoryMovementResponseDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .type(entity.getType())
                .sourceLocationId(
                        entity.getSourceLocation() != null
                                ? entity.getId()
                        :null
                )
                .destinationLocationId(
                        entity.getDestinationLocation() != null
                        ? entity.getDestinationLocation().getId()
                                : null
                )
                .quantity(entity.getQuantity())
                .reason(entity.getReason())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
