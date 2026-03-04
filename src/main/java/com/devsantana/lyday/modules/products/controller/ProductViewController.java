package com.devsantana.lyday.modules.products.controller;

import com.devsantana.lyday.modules.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/ui/products")
    public String list(Model model){
        model.addAttribute("products",
                productService.findAll(Pageable.unpaged())
                        .getContent());
        return "products/list";
    }
}