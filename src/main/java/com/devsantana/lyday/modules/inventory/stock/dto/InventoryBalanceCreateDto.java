package com.devsantana.lyday.modules.inventory.stock.dto;

import lombok.Data;

@Data
public class InventoryBalanceCreateDto {

    private Long productId;
    private Long locationId;
    private Integer quantity;
}
