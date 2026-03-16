package com.devsantana.lyday.modules.company.location.model;

import com.devsantana.lyday.modules.company.warehouses.model.Warehouse;
import com.devsantana.lyday.shared.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"warehouse_id", "street", "level", "position"}
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends BaseEntity {

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
}
