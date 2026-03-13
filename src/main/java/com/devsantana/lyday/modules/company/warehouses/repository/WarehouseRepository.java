package com.devsantana.lyday.modules.company.warehouses.repository;

import com.devsantana.lyday.modules.company.warehouses.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findByBranchId(Long branchId);

    boolean existsByCode(String code);
}
