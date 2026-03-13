package com.devsantana.lyday.modules.company.branch.service;

import com.devsantana.lyday.modules.company.branch.dto.BranchResponseDto;
import com.devsantana.lyday.modules.company.branch.dto.UpdateBranch;
import com.devsantana.lyday.modules.company.branch.dto.CreateBranchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BranchService {

    BranchResponseDto create(CreateBranchDto dto);

    BranchResponseDto findById(Long id);

    List<BranchResponseDto> findAll(Pageable pageable);

    BranchResponseDto update(Long id, UpdateBranch updateBranch);

    void delete(Long id);
}
