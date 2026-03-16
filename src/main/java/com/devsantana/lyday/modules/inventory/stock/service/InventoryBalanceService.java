package com.devsantana.lyday.modules.inventory.stock.service;

import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceCreateDto;
import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceResponseDto;

import java.util.List;

public interface InventoryBalanceService {

    InventoryBalanceResponseDto create(InventoryBalanceCreateDto dto);

    InventoryBalanceResponseDto findByProductIdAndLocationId(Long productId, Long locationId);

    List<InventoryBalanceResponseDto> findByProduct(Long productId);

    List<InventoryBalanceResponseDto> findByLocation(Long locationId);
}
