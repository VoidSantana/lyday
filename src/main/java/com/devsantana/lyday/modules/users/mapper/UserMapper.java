package com.devsantana.lyday.modules.users.mapper;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;
import com.devsantana.lyday.modules.users.dto.UserUpdateDto;
import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    // Converte o CreateDto -> Entity
    public static User toEntity(UserCreateDto dto, Set<Role> roles){
        return User.builder()
                .username(dto.getUsername())
                .enabled(true)
                .roles(roles)
                .build();
    }

    // Converte o Entity -> ResponseDto
    public static UserResponseDto toResponseDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .build();
    }

    // Atualiza entity com UpdateDto (roles e enabled)
    public static void updateEntity(User user, Set<Role> roles, UserUpdateDto dto){
        user.setRoles(roles);
        user.setEnabled(dto.isEnabled());
    }
}
