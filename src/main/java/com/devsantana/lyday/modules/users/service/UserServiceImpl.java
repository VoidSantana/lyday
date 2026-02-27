package com.devsantana.lyday.modules.users.service;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;
import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;
import com.devsantana.lyday.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto create(UserCreateDto dto){

        // Regra: username não pode repetir
        if (userRepository.existsByUsername(dto.getUsername())){
            throw new IllegalArgumentException("Username já existe");
        }

        // Converte String -> Enum Role
        Set<Role> roles = dto.getRoles()
                .stream()
                .map(String::trim)
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(String::toUpperCase)
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true)
                .roles(roles)
                .build();
        User saved = userRepository.save(user);

        return UserResponseDto.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .enabled(saved.isEnabled())
                .roles(saved.getRoles()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .build();
    }
}