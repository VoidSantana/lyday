package com.devsantana.lyday.modules.company.branch.mapper;

import com.devsantana.lyday.modules.company.branch.branchdto.BranchResponseDto;
import com.devsantana.lyday.modules.company.branch.branchdto.CreateBranchDto;
import com.devsantana.lyday.modules.company.branch.model.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public static Branch toEntity(CreateBranchDto dto){
        return Branch.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .state(dto.getState())
                .build();
    }

    public static BranchResponseDto toDto(Branch branch){
        return BranchResponseDto.builder()
                .id(branch.getId())
                .name(branch.getName())
                .city(branch.getCity())
                .state(branch.getState())
                .build();
    }
}