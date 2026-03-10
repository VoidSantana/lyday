package com.devsantana.lyday.modules.company.branch.branchdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateBranchDto {
    @NotBlank
    private String name;

    private String city;

    private String state;
}