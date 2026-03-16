package com.devsantana.lyday.modules.inventory.stock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryBalanceResponseDto {

    private Long id;

    private Long productId;

    private Long locationId;

    private Integer quantity;
}
