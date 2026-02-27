package com.devsantana.lyday.modules.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UserCreateDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotEmpty
    private Set<String> roles;

}