package com.devsantana.lyday.modules.products.service;


import com.devsantana.lyday.modules.products.dto.ProductResponseDto;
import com.devsantana.lyday.modules.products.dto.ProductCreateDto;
import com.devsantana.lyday.modules.products.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDto create(ProductCreateDto dto);

    ProductResponseDto update(Long id, ProductUpdateDto dto);

    ProductResponseDto findById(Long id);

    ProductResponseDto findBySku(String sku);

    Page<ProductResponseDto> findAll(Pageable pageable);

    void delete(Long id);
}