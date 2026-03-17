package com.devsantana.lyday.modules.inventory.movement.repository;

import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InventoryMovementRepository
        extends JpaRepository<InventoryMovement, Long>,
        JpaSpecificationExecutor<InventoryMovement> {

    List<InventoryMovement> findByProductId(Long productId);

}
