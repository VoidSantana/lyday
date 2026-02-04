package com.devsantana.lyday.modules.products.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class PrimeiroEndPointTest {
    @GetMapping("/ping")
    public String ping(){
        return "OK";
    }
}
