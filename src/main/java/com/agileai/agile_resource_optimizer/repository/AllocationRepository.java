package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
}
