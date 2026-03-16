package com.devsantana.lyday.modules.inventory.stock.controller;

import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceCreateDto;
import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceResponseDto;
import com.devsantana.lyday.modules.inventory.stock.service.InventoryBalanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/stock")
@RequiredArgsConstructor
public class InventoryBalanceController {

    private final InventoryBalanceServiceImpl inventoryBalanceService;

    @PostMapping
    public InventoryBalanceResponseDto create(@RequestBody InventoryBalanceCreateDto dto){
        return inventoryBalanceService.create(dto);
    }
    @GetMapping("/product/{productId}")
    public List<InventoryBalanceResponseDto> findByProduct(@PathVariable Long productId){
        return inventoryBalanceService.findByProduct(productId);
    }
    @GetMapping("/location/{locationId}")
    public List<InventoryBalanceResponseDto> findByLocation(@PathVariable Long locationId){
        return inventoryBalanceService.findByLocation(locationId);
    }
    @GetMapping("/{productId}/{locationId}")
    public InventoryBalanceResponseDto findByProductAndLocation(
            @PathVariable Long productId,
            @PathVariable Long locationId
    ){
        return inventoryBalanceService.findByProductIdAndLocationId(productId, locationId);
    }
}
