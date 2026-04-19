package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.entity.Task;
import com.agileai.agile_resource_optimizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public Task create(@RequestBody Task task) {
        return service.save(task);
    }

    @GetMapping
    public List<Task> getAll() {
        return service.getAll();
    }
}