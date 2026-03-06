package com.devsantana.lyday.modules.users.service;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserUpdatePasswordDto;
import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;
import com.devsantana.lyday.modules.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createShouldPersistUsernameIsAvailable(){
        UserCreateDto dto = new UserCreateDto();
        dto.setUsername("maria");
        dto.setPassword("password");
        dto.setRoles(Set.of("admin"));

        when(userRepository.existsByUsername("maria")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("password-codify");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(10L);
            return user;
        });

        var response = userService.create(dto);

        assertEquals(10L, response.getId());
        assertEquals("maria", response.getUsername());
        assertTrue(response.isEnabled());
        assertTrue(response.getRoles().contains("ROLE_ADMIN"));
        verify(userRepository).save(any(User.class));
    }
    @Test
    void createShouldThrowWhenUsernameAlreadyExists() {
        UserCreateDto dto = new UserCreateDto();

        dto.setUsername("duplicado");
        dto.setPassword("x");
        dto.setRoles(Set.of("user"));

        when(userRepository.existsByUsername("duplicado")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.create(dto));
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void updateShouldThrowWhenUserDoesNotExist() {
        UserUpdatePasswordDto dto = new UserUpdatePasswordDto();
        dto.setNewPassword("new-password");

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updatePassword(99L, dto));
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void updatePasswordShouldEncoderAndPersistNewPassword() {
        User user = User.builder()
                .id(7L)
                .username("joao")
                .password("old-password")
                .roles(Set.of(Role.ROLE_USER))
                .enabled(true)
                .build();
        UserUpdatePasswordDto dto = new UserUpdatePasswordDto();
        dto.setNewPassword("new-password");

        when(userRepository.findById(7L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("new-password")).thenReturn("encoded-new-password");

        userService.updatePassword(7L, dto);

        assertEquals("encoded-new-password", user.getPassword());
        verify(userRepository.save(user));
    }
}