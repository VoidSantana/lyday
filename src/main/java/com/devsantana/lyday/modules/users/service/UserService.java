package com.devsantana.lyday.modules.users.service;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;

public interface UserService {
    // Bug começou aqui investigar depois, @Override do UserServiceImpl
    UserResponseDto create(UserCreateDto dto);
}