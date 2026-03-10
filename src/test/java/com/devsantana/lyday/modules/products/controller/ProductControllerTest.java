package com.devsantana.lyday.modules.products.controller;

import com.devsantana.lyday.config.security.JwtAuthenticationFilter;
import com.devsantana.lyday.config.security.JwtTokenService;
import com.devsantana.lyday.modules.products.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import support.ProductTestDataFactory;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateProduct() throws Exception{
        var dto = ProductTestDataFactory.createDto();
        var response = ProductTestDataFactory.response();

        when(productService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Notebook Gamer"));
    }
    @Test
    void shouldReturnProductById() throws Exception{
        var response = ProductTestDataFactory.response();

        when(productService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU-123"));
    }
    @Test
    void shouldReturnProductBySku() throws Exception{
        var response = ProductTestDataFactory.response();

        when(productService.findBySku("SKU-123")).thenReturn(response);

        mockMvc.perform(get("/api/products/sku/SKU-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU-123"));
    }
    @Test
    void shouldReturnProductPage() throws Exception{
        var response = ProductTestDataFactory.response();

        when(productService.findAll(any())).thenReturn(new PageImpl<>(List.of(response)));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Notebook Gamer"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateProduct() throws Exception{
        var dto = ProductTestDataFactory.updateDto();
        var response = ProductTestDataFactory.response();

        when(productService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProduct() throws Exception{
        doNothing().when(productService).delete(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
