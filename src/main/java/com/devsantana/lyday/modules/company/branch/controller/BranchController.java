package com.devsantana.lyday.modules.company.branch.branchcontroller;

import com.devsantana.lyday.modules.company.branch.branchdto.BranchResponseDto;
import com.devsantana.lyday.modules.company.branch.branchdto.BranchUpdate;
import com.devsantana.lyday.modules.company.branch.branchdto.CreateBranchDto;
import com.devsantana.lyday.modules.company.branch.branchservice.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public BranchResponseDto create(@RequestBody @Valid CreateBranchDto dto){
        return branchService.create(dto);
    }
    @GetMapping
    public List<BranchResponseDto> findAll(Pageable pageable){
        return branchService.findAll(pageable);
    }
    @GetMapping("/id")
    public BranchResponseDto findById(@PathVariable Long id){
        return branchService.findById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDto> update(@PathVariable Long id, @Valid @RequestBody BranchUpdate dto){
        return ResponseEntity.ok(branchService.update(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}