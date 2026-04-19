package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.entity.Task;
import com.agileai.agile_resource_optimizer.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public Task save(Task task) {
        return repo.save(task);
    }

    public List<Task> getAll() {
        return repo.findAll();
    }
}