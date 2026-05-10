package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.Allocation;
import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.Sprint;
import com.agileai.agile_resource_optimizer.model.Task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    List<Allocation> findBySprint_Id(Long sprintId);
    List<Allocation> findByTask_Id(Long taskId);
    List<Allocation> findByTask_IdAndStatusNot(Long taskId, String status);
    boolean existsBySprintAndTaskAndDeveloper(Sprint sprint, Task task, Developer developer);

    /**
     * Finds all RECOMMENDED allocations for a developer in a sprint,
     * excluding the task they were just approved for.
     * Used to auto-decline D4's other recommendations when D4 is approved for one task
     * (prevents overutilization across multiple tasks in the same sprint).
     */
    List<Allocation> findByDeveloperAndSprint_IdAndStatusAndTask_IdNot(
        Developer developer, Long sprintId, String status, Long excludeTaskId
    );
}
