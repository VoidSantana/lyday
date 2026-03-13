package com.devsantana.lyday.modules.company.branch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBranch {
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String state;

}
