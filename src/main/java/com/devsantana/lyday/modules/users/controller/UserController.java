package com.devsantana.lyday.modules.users.controller;

import com.devsantana.lyday.modules.users.dto.UserCreateDto;
import com.devsantana.lyday.modules.users.dto.UserResponseDto;
import com.devsantana.lyday.modules.users.dto.UserUpdateDto;
import com.devsantana.lyday.modules.users.dto.UserUpdatePasswordDto;
import com.devsantana.lyday.modules.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Aplica em todos os METHODS desse controller
public class UserController {

    private final UserService userService;
    // =========== CREATE ===============================
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto dto){
        UserResponseDto created = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    // =========== FIND BY ID ===========================
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
    // =========== FIND ALL =============================
    @GetMapping
    public Page<UserResponseDto> findAll(Pageable pageable){
        return userService.findAll(pageable);
    }
    // =========== UPDATE (roles/enabled ================
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateDto dto){
        return ResponseEntity.ok(userService.update(id, dto));
    }
    // =========== UPDATE PASSWORD ======================
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @Valid @RequestBody UserUpdatePasswordDto dto){
        userService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }
    // =========== DELETE ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}