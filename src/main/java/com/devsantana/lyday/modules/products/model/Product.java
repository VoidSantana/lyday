package com.devsantana.lyday.modules.products.model;

import com.devsantana.lyday.shared.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_protuct_name", columnList = "name"),
                @Index(name = "idx_product_sku", columnList = "sku")
        }
        )
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;// nome do produto

    @Column(nullable = false, unique = true, length = 50)
    private String sku;// sku do produto

    @Column(nullable = false)
    private Integer stock;// quantidade do produto

    @Column(nullable = false)
    private Double weightKg;// peso do produto

    @Column(nullable = false)
    private Integer volumeCm3;// cubagem do produto

    @Column(length = 500)
    private String description;// descrição do produto

    @Column(length = 100)
    private String brand;// marca do produto

    @Column(nullable = false)
    private double price;// preço do produto
}
