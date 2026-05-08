package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.model.Task;
import com.agileai.agile_resource_optimizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @GetMapping
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }
}
