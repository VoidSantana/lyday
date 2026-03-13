package com.devsantana.lyday.modules.company.warehouses.model;

import com.devsantana.lyday.modules.company.branch.model.Branch;
import com.devsantana.lyday.shared.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}
