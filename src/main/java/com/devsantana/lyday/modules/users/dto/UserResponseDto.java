package com.devsantana.lyday.modules.users.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String username;
    private boolean enabled;
    private Set<String> roles;
}