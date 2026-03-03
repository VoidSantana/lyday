package com.devsantana.lyday.modules.users.service;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;
import com.devsantana.lyday.modules.users.dto.UserUpdateDto;
import com.devsantana.lyday.modules.users.dto.UserUpdatePasswordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponseDto create(UserCreateDto dto);

    UserResponseDto findById(Long id);

    Page<UserResponseDto> findAll(Pageable pageable);

    UserResponseDto update(Long id, UserUpdateDto dto);

    void updatePassword(Long id, UserUpdatePasswordDto dto);

    void delete(Long id);
}