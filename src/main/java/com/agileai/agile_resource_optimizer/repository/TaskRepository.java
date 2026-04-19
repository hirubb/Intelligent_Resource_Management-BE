package com.agileai.agile_resource_optimizer.repository;

import com.agileai.agile_resource_optimizer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {}