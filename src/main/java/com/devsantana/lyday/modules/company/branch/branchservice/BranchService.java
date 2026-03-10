package com.devsantana.lyday.modules.company.branch.branchservice;

import com.devsantana.lyday.modules.company.branch.branchdto.BranchResponseDto;
import com.devsantana.lyday.modules.company.branch.branchdto.BranchUpdate;
import com.devsantana.lyday.modules.company.branch.branchdto.CreateBranchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BranchService {

    BranchResponseDto create(CreateBranchDto dto);

    BranchResponseDto findById(Long id);

    List<BranchResponseDto> findAll(Pageable pageable);

    BranchResponseDto update(Long id, BranchUpdate branchUpdate);

    void delete(Long id);
}
