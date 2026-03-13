package com.devsantana.lyday.modules.company.branch.service;

import com.devsantana.lyday.modules.company.branch.dto.BranchResponseDto;
import com.devsantana.lyday.modules.company.branch.dto.UpdateBranch;
import com.devsantana.lyday.modules.company.branch.dto.CreateBranchDto;
import com.devsantana.lyday.modules.company.branch.repository.BranchRepository;
import com.devsantana.lyday.modules.company.branch.mapper.BranchMapper;
import com.devsantana.lyday.modules.company.branch.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService{

    private final BranchRepository branchRepository;

    @Override
    public BranchResponseDto create(CreateBranchDto dto) {
        if (branchRepository.existsByName(dto.getName())){
            throw new RuntimeException("Branch já Existe");
        }
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
    public List<BranchResponseDto> findAll(Pageable pageable){
        return branchRepository.findByDeletedFalse(pageable)
                .map(BranchMapper::toDto)
                .getContent();
    }

    @Override
    public BranchResponseDto update(Long id, UpdateBranch dto) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Branch not found"));
        branch.setName(dto.getName());
        branch.setCity(dto.getCity());
        branch.setState(dto.getState());

        Branch updated = branchRepository.save(branch);
        return BranchMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Branch Not Found"));

        branch.setDeleted(true);
        branchRepository.save(branch);
    }

}
