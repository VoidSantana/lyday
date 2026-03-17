package com.devsantana.lyday.modules.inventory.movement.service;

import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementCreateDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementFilterDto;
import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryMovementService {

    InventoryMovementResponseDto createMovement(
            InventoryMovementCreateDto dto
    );

    List<InventoryMovementResponseDto> findByProduct(Long productId);

    Page<InventoryMovementResponseDto> search(
            InventoryMovementFilterDto filter,
            Pageable pageable
    );

}
