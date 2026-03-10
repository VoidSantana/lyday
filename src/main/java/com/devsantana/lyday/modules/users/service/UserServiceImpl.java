package com.devsantana.lyday.modules.users.service;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;
import com.devsantana.lyday.modules.users.dto.UserUpdateDto;
import com.devsantana.lyday.modules.users.dto.UserUpdatePasswordDto;
import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;
import com.devsantana.lyday.modules.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // ==============================
    // CREATE
    // ==============================
    @Override
    public UserResponseDto create(UserCreateDto dto){

        // Regra: username não pode repetir
        if (userRepository.existsByUsername(dto.getUsername())){
            throw new IllegalArgumentException("Username já existe. ");
        }

        // Converte String -> Enum Role
        Set<Role> roles = parseRoles(dto.getRoles());


        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true) // Usuario nasce ativo
                .roles(roles)
                .build();
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    // ==============================
    // FIND BY ID
    // ==============================
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado. "));
        return toResponse(user);
    }

    // ==============================
    // FIND ALL
    // ==============================
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(this::toResponse);
    }

    // ==============================
    // UPDATE (roles + enabled). Username não muda
    // ==============================
    @Override
    public UserResponseDto update(Long id, UserUpdateDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado para atualização. "));

        Set<Role> roles = parseRoles(dto.getRoles());
        user.setRoles(roles);
        user.setEnabled(dto.isEnabled());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    // ==============================
    // UPDATE PASSWORD
    // ==============================
    public void updatePassword(Long id, UserUpdatePasswordDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado para alteração da senha. "));
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // ==============================
    // DELETE
    // ==============================
    public void delete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Usuario não encontrado para deleção. "));

        user.setEnabled(false);
        userRepository.save(user);
    }

    private Set<Role> parseRoles(Set<String> roles){
        return roles.stream()
                .map(String::trim)
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(String::toUpperCase)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
    private UserResponseDto toResponse(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .roles(user.getRoles()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .build();
    }
}