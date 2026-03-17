package com.devsantana.lyday.modules.inventory.movement.dto;

import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovementType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryMovementFilterDto {

    private Long productId;

    private Long locationId;

    private InventoryMovementType type;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;
}
