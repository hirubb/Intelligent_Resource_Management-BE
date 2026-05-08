package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
