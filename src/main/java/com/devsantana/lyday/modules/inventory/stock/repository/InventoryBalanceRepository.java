package com.devsantana.lyday.modules.inventory.stock.repository;

import com.devsantana.lyday.modules.inventory.stock.model.InventoryBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InventoryBalanceRepository extends JpaRepository<InventoryBalance, Long> {

    Optional<InventoryBalance> findByProductIdAndLocationId(Long productId, Long locationId);

    List<InventoryBalance> findByProductId(Long productId);

    List<InventoryBalance> findByLocationId(Long locationId);
}
