package com.devsantana.lyday.modules.inventory.movement.repository;

import com.devsantana.lyday.modules.inventory.movement.dto.InventoryMovementFilterDto;
import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InventoryMovementSpecification {

    public static Specification<InventoryMovement> filter(
            InventoryMovementFilterDto filter
    ){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getProductId() != null){
                predicates.add(
                        cb.equal(
                                root.get("product").get("id"),
                                filter.getProductId()
                        )
                );
            }
            if (filter.getLocationId()!=null){
                predicates.add(
                        cb.or(
                                cb.equal(
                                        root.get("sourceLocation").get("id"),
                                        filter.getLocationId()
                                ),
                                cb.equal(
                                        root.get("destinationLocation").get("id"),
                                        filter.getLocationId()
                                )
                        )
                );
            }
            if (filter.getType()!=null){
                predicates.add(
                        cb.equal(root.get("type"), filter.getType())
                );
            }
            if (filter.getDateStart()!=null){
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                filter.getDateStart()
                        )
                );
            }
            if (filter.getDateEnd()!=null){
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("createdAt"),
                                filter.getDateEnd()
                        )
                );
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
