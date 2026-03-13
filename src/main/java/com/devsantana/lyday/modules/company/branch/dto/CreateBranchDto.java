package com.devsantana.lyday.modules.company.branch.branchdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBranchDto {
    @NotBlank
    private String name;

    private String city;

    private String state;
}