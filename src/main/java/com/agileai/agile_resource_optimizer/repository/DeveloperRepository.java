package com.agileai.agile_resource_optimizer.repository;



import com.agileai.agile_resource_optimizer.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {}