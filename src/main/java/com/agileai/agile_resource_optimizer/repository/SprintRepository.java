package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    Optional<Sprint> findBySprintId(String sprintId);

    List<Sprint> findByStatus(String status);
}
