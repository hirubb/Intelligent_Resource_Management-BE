package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}