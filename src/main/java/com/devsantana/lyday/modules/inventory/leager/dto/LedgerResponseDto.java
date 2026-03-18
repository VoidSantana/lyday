package com.devsantana.lyday.modules.inventory.leager.dto;

import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovementType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LedgerResponseDto {

    private LocalDateTime date;

    private InventoryMovementType type;

    private Integer entry;

    private Integer exit;

    private Integer balance;

    private String reason;
}
