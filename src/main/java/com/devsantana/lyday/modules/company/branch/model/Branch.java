package com.devsantana.lyday.modules.company.branch.model;

import com.devsantana.lyday.shared.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String city;

    private String state;
}