package com.devsantana.lyday.modules.products.service;

import com.devsantana.lyday.modules.products.dto.ProductResponseDto;
import com.devsantana.lyday.modules.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import support.ProductTestDataFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProduct(){
        var dto = ProductTestDataFactory.createDto();
        var entity = ProductTestDataFactory.entity();

        when(productRepository.existsBySku(dto.getSku())).thenReturn(false);
        when(productRepository.save(any())).thenReturn(entity);

        ProductResponseDto response = productService.create(dto);

        assertNotNull(response);
        assertEquals(dto.getName(), response.getName());
    }
    @Test
    void shouldThrowExceptionWhenSkuExists(){

        var dto = ProductTestDataFactory.createDto();

        when(productRepository.existsBySku(dto.getSku())).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> productService.create(dto)
        );
    }
    @Test
    void shouldFindProductById(){

        var entity = ProductTestDataFactory.entity();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        var response = productService.findById(1L);

        assertEquals(entity.getName(), response.getName());
    }
    @Test
    void shouldFindProductBySku(){

        var entity = ProductTestDataFactory.entity();

        when(productRepository.findBySku("SKU-123"))
                .thenReturn(Optional.of(entity));

        var response = productService.findBySku("SKU-123");

        assertEquals("SKU-123", response.getSku());
    }
    @Test
    void shouldDeleteProduct(){
        when(productRepository.existsById(1L)).thenReturn(true);
        productService.delete(1L);
        verify(productRepository).deleteById(1L);
    }
}
