package com.devsantana.lyday.modules.users.controller;

import com.devsantana.lyday.config.security.JwtAuthenticationFilter;
import com.devsantana.lyday.config.security.JwtTokenService;
import com.devsantana.lyday.modules.users.dto.*;
import com.devsantana.lyday.modules.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() throws Exception{

        UserResponseDto response = UserResponseDto.builder()
                .id(1L)
                .username("maria")
                .enabled(true)
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        when(userService.create(org.mockito.ArgumentMatchers.any())).thenReturn(response);

        String json = """
                {
                    "username": "maria",
                    "password": "123456",
                    "roles": ["ADMIN"]
                }
                """;

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("maria"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUserById() throws Exception {
        UserResponseDto response = UserResponseDto.builder()
                .id(1L)
                .username("joao")
                .enabled(true)
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("joao"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUserPage() throws Exception{

        UserResponseDto user = UserResponseDto.builder()
                .id(1L)
                .username("maria")
                .enabled(true)
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        when(userService.findAll(org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].username").value("maria"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception{

        UserResponseDto response = UserResponseDto.builder()
                .id(1L)
                .username("maria")
                .enabled(false)
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userService.update(org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(response);

        String json = """
                {
                "enabled": false,
                "roles": ["USER"]
                }
                """;

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled").value(false));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdatePassword() throws Exception{

        String json = """
                {
                "newPassword": "novaSenha"
                }
                """;

        doNothing().when(userService)
                .updatePassword(org.mockito.ArgumentMatchers.eq(1L),
                        org.mockito.ArgumentMatchers.any());

        mockMvc.perform(put("/api/users/1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUser() throws Exception{

        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
