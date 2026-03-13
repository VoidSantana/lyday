package com.devsantana.lyday.modules.company.branch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchResponseDto {
    private Long id;
    private String name;
    private String city;
    private String state;
}