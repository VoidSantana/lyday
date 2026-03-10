package com.devsantana.lyday.modules.users.service;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;
import com.devsantana.lyday.modules.users.dto.UserUpdateDto;
import com.devsantana.lyday.modules.users.dto.UserUpdatePasswordDto;
import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;
import com.devsantana.lyday.modules.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void  shouldDisableUserWhenDeleteIsCalled(){
        User user = User.builder()
                .id(1L)
                .username("maria")
                .enabled(false)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        assertFalse(user.isEnabled());

        verify(userRepository).save(user);
    }
    @Test
    void shouldCreateUserSucessfully(){
        UserCreateDto dto = new UserCreateDto();
        dto.setUsername("maria");
        dto.setPassword("123456");
        dto.setRoles(Set.of("ADMIN"));

        when(userRepository.existsByUsername("maria")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encoded-password");

        User savedUser = User.builder()
                .id(1L)
                .username("maria")
                .password("encoded-password")
                .enabled(true)
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.create(dto);

        assertEquals("maria", response.getUsername());

        verify(userRepository).save(any(User.class));
    }
    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists(){

        UserCreateDto dto = new UserCreateDto();
        dto.setUsername("maria");

        when(userRepository.existsByUsername("maria")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.create(dto));

        verify(userRepository, never()).save(any());
    }
    @Test
    void shouldReturnUserWhenIdExists(){
        User user = User.builder()
                .id(1L)
                .username("maria")
                .password("123456")
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto response = userService.findById(1L);

        assertEquals("maria", response.getUsername());
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
    }
    @Test
    void shouldUpdateUser(){
        User user = User.builder()
                .id(1L)
                .username("maria")
                .enabled(true)
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();

        UserUpdateDto dto = new UserUpdateDto();
        dto.setEnabled(false);
        dto.setRoles(Set.of("USER"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto response = userService.update(1L, dto);
        assertFalse(response.isEnabled());
    }
    @Test
    void shouldUpdatePassword(){
        User user = User.builder()
                .id(1L)
                .password("old-password")
                .build();

        UserUpdatePasswordDto dto = new UserUpdatePasswordDto();
        dto.setNewPassword("new-password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("new-password")).thenReturn("encoded-password");

        userService.updatePassword(1L, dto);

        verify(userRepository).save(user);
    }
}
