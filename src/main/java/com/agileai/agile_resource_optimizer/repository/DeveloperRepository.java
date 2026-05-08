package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    @Query("SELECT d FROM Developer d WHERE d.dev_id = :dev_id")
    Optional<Developer> findByDev_id(@Param("dev_id") String dev_id);
}