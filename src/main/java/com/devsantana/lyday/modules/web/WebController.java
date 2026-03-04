package com.devsantana.lyday.modules.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/ui/login")
    public String loginPage(){
        return "auth/login"; //templates/auth/login.html
    }
    @GetMapping("/ui/users")
    public String userPage(){
        return "user/list";
    }
}