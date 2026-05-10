package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.DeveloperProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
    Optional<DeveloperProfile> findByDeveloperMetricsId(Long developerId);
}
