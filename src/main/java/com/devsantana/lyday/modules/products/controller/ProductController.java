package com.devsantana.lyday.modules.products.controller;

import com.devsantana.lyday.modules.products.dto.ProductCreateDto;
import com.devsantana.lyday.modules.products.dto.ProductResponseDto;
import com.devsantana.lyday.modules.products.dto.ProductUpdateDto;
import com.devsantana.lyday.modules.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    // =========================================
    //          ========CREATED===========
    // =========================================
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(
            @Valid @RequestBody ProductCreateDto dto) {
        ProductResponseDto created = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    // =========================================
    //       ========FIND BY ID===========
    // =========================================
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }
    // =========================================
    //          ========FIND BY SKU===========
    // =========================================
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDto> findBySku(@PathVariable String sku){
        return ResponseEntity.ok(productService.findBySku(sku));
    }
    // =========================================
    //          ========FIND ALL===========
    // =========================================
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(productService.findAll(pageable));
    }
    // =========================================
    //          ========UPDATE===========
    // =========================================
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody ProductUpdateDto dto){
        return ResponseEntity.ok(productService.update(id, dto));
    }
    // =========================================
    //          ========DELETE===========
    // =========================================
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
}