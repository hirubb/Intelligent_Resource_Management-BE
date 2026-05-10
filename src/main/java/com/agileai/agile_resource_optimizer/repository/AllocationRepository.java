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
    boolean existsBySprintAndTaskAndDeveloper(Sprint sprint, Task task, Developer developer);
    


}
