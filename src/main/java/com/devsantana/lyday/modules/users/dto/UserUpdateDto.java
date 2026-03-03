package com.devsantana.lyday.modules.users.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {

    //Roles do usuario (ROLE_ADMIN / ROLE_USER)
    @NotEmpty
    private Set<String> roles;

    // Ativar/desativar usuario
    private boolean enabled;
}
