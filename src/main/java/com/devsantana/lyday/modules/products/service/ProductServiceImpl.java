package com.devsantana.lyday.modules.products.service;


import com.devsantana.lyday.modules.products.dto.ProductCreateDto;
import com.devsantana.lyday.modules.products.dto.ProductResponseDto;
import com.devsantana.lyday.modules.products.dto.ProductUpdateDto;
import com.devsantana.lyday.modules.products.mapper.ProductMapper;
import com.devsantana.lyday.modules.products.model.Product;
import com.devsantana.lyday.modules.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    // ==================================
    // CREATE
    // ==================================
    @Override
    public ProductResponseDto create(ProductCreateDto dto){
        if (productRepository.existsBySku(dto.getSku())){
            throw new IllegalArgumentException("SKU já Cadastrada");
        }

        Product product = ProductMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }
    // ==================================
    // UPDATE
    // ==================================
    @Override
    public ProductResponseDto update(Long id, ProductUpdateDto dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        ProductMapper.updateEntity(product, dto);
        return ProductMapper.toResponse(product);
    }
    // ==================================
    // FIND BY ID
    // ==================================
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        return ProductMapper.toResponse(product);
    }
    // ==================================
    // FIND BY SKU
    // ==================================
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findBySku(String sku){
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() ->new EntityNotFoundException("Produto não encontrado"));
        return ProductMapper.toResponse(product);
    }
    // ==================================
    // FIND ALL
    // ==================================
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAll(Pageable pageable){
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }
    // ==================================
    // DELETE
    // ==================================
    @Override
    public void delete(Long id){
        if (!productRepository.existsById(id)){
            throw new EntityNotFoundException("Produto não encontrado");
        }
        productRepository.deleteById(id);
    }
}