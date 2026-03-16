package com.devsantana.lyday.modules.company.location.repository;

import com.devsantana.lyday.modules.company.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByWarehouseId(Long warehouseId);

    Optional<Location> findByCode(String code);

    boolean existsByWarehouseIdAndStreetAndLevelAndPosition(
            Long warehouseId,
            String street,
            String level,
            String position
    );
}