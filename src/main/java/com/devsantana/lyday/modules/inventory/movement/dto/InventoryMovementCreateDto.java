package com.devsantana.lyday.modules.inventory.movement.dto;

import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovementType;
import lombok.Data;

@Data
public class InventoryMovementCreateDto {

    private Long productId;

    private InventoryMovementType type;

    private Long sourceLocationId;

    private Long destinationLocationId;

    private Integer quantity;

    private String reason;
}
