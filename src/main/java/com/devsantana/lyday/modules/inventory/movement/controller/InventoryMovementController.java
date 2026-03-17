package com.devsantana.lyday.modules.inventory.movement.controller;

import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementCreateDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementFilterDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementResponseDto;
import com.devsantana.lyday.modules.inventory.movement.service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final InventoryMovementService movementService;

    @PostMapping
    public InventoryMovementResponseDto create(
            @RequestBody InventoryMovementCreateDto dto
            ){
        return movementService.createMovement(dto);
    }
    @GetMapping("/product/{productId}")
    public List<InventoryMovementResponseDto> findByProduct(
            @PathVariable Long productId
    ){
        return movementService.findByProduct(productId);
    }
    @GetMapping
    public Page<InventoryMovementResponseDto> search(
            InventoryMovementFilterDto filter,
            Pageable pageable
    ){
        return movementService.search(filter, pageable);
    }
}
