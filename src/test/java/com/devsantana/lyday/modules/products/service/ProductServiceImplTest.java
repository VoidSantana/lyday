package com.devsantana.lyday.modules.products.service;

import com.devsantana.lyday.modules.products.dto.ProductCreateDto;
import com.devsantana.lyday.modules.products.dto.ProductUpdateDto;
import com.devsantana.lyday.modules.products.model.Product;
import com.devsantana.lyday.modules.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createShouldPersistProductWhenSkuIsAvailable() {
        ProductCreateDto dto = new ProductCreateDto();
        dto.setName("Notebook");
        dto.setSku("SKU-123");
        dto.setStock(50);
        dto.setWeightKg(1.8);
        dto.setVolumeCm3(1000);
        dto.setDescription("Produto teste");
        dto.setBrand("ACME");
        dto.setPrice(3500.0);

        when(productRepository.existsBySku("SKU-123")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product entity = invocation.getArgument(0);
            entity.setId(44L);
            return entity;
        });

        var response = productService.create(dto);

        assertEquals(44L, response.getId());
        assertEquals("SKU-123", response.getSku());
        assertEquals(50, response.getStock());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createShouldThrowWhenSkuAlreadyExists() {
        ProductCreateDto dto = new ProductCreateDto();
        dto.setSku("SKU-123");

        when(productRepository.existsBySku("SKU-123")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> productService.create(dto));
        verify(productRepository, never()).save(any(Product.class));
    }
    @Test
    void updateShouldThrowWhenProductNotFound() {
        ProductUpdateDto dto = new ProductUpdateDto();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.update(1L, dto));
    }
    @Test
    void deleteShouldThrowWhenProductNotFound() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> productService.delete(99L));
        verify(productRepository, never()).deleteById(anyLong());
    }
    @Test
    void deleteShouldDeleteWhenProductExist() {
        when(productRepository.existsById(55L)).thenReturn(true);

        productService.delete(55L);

        verify(productRepository).deleteById(55L);
    }
}
