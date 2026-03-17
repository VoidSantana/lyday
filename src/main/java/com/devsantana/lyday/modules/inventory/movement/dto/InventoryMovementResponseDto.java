package com.devsantana.lyday.modules.inventory.movement.dto;

import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovementType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryMovementResponseDto {

    private Long id;

    private Long productId;

    private InventoryMovementType type;

    private Long sourceLocationId;

    private Long destinationLocationId;

    private Integer quantity;

    private String reason;

    private LocalDateTime createdAt;
}
