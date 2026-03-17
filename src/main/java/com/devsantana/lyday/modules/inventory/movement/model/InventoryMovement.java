package com.devsantana.lyday.modules.inventory.movement.model;

import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.products.model.Product;
import com.devsantana.lyday.shared.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryMovement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryMovementType type;

    @ManyToOne
    @JoinColumn(name = "source_location_id")
    private Location sourceLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 500)
    private String reason;
}
