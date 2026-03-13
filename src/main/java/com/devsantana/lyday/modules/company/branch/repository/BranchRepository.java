package com.devsantana.lyday.modules.company.branch.repository;

import com.devsantana.lyday.modules.company.branch.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByName(String name);

    boolean existsByName(String name);

    List<Branch> findByCity(String city);

    Page<Branch> findByDeletedFalse(Pageable pageable);
}