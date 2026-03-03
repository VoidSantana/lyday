package com.devsantana.lyday.modules.users.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserUpdatePasswordDto {

    @NotEmpty
    private String newPassword;
}
