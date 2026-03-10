package com.devsantana.lyday.modules.company.branch.branchservice;

import com.devsantana.lyday.modules.company.branch.branchdto.BranchResponseDto;
import com.devsantana.lyday.modules.company.branch.branchdto.BranchUpdate;
import com.devsantana.lyday.modules.company.branch.branchdto.CreateBranchDto;
import com.devsantana.lyday.modules.company.branch.branchrepository.BranchRepository;
import com.devsantana.lyday.modules.company.branch.mapper.BranchMapper;
import com.devsantana.lyday.modules.company.branch.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService{

    private BranchRepository branchRepository;

    @Override
    public BranchResponseDto create(CreateBranchDto dto) {
        Branch branch = BranchMapper.toEntity(dto);
        Branch saved = branchRepository.save(branch);
        return BranchMapper.toDto(saved);
    }

    @Override
    public BranchResponseDto findById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found. "));
        return BranchMapper.toDto(branch);
    }

    @Override
    public List<BranchResponseDto> findAll(Pageable pageable) {
        return branchRepository.findAll()
                .stream()
                .map(BranchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponseDto update(Long id, BranchUpdate branchUpdate) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
