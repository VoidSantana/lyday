package com.devsantana.lyday.modules.products.repository;

import com.devsantana.lyday.modules.products.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import support.ProductTestDataFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EnableJpaAuditing
public class ProductRepositoryTest {

    @Autowired
    public ProductRepository productRepository;

    @Test
    void shouldSaveProduct(){

        var product = ProductTestDataFactory.entity();

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
    }
    @Test
    void shouldFindBySku(){

        var product = ProductTestDataFactory.entity();

        productRepository.save(product);

        Optional<Product> found = productRepository.findBySku("SKU-123");

        assertTrue(found.isPresent());
    }
    @Test
    void shouldCheckIfSkuExists(){

        var product = ProductTestDataFactory.entity();
        productRepository.save(product);
        boolean exists =
                productRepository.existsBySku("SKU-123");
        assertTrue(exists);
    }
}
