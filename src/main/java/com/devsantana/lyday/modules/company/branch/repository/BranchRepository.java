package com.devsantana.lyday.modules.company.branch.branchrepository;

import com.devsantana.lyday.modules.company.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}