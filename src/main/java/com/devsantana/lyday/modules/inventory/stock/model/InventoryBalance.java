package com.devsantana.lyday.modules.inventory.stock.model;

import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.products.model.Product;
import com.devsantana.lyday.shared.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "inventory_balance",
uniqueConstraints = {
                @UniqueConstraint(
                        columnNames ={
                                "product_id",
                                "location_id"
                        }
                        )
        }
        )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryBalance extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private Integer quantity;
}
