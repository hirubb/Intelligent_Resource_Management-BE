package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.model.Task;
import com.agileai.agile_resource_optimizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
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

    @GetMapping("/sprint/{sprintId}")
    public List<Task> getTasksBySprintId(@PathVariable Long sprintId) {
        return taskService.getTasksBySprintId(sprintId);
    }

    @GetMapping("/developer/{developerId}")
    public List<Task> getTasksByDeveloperId(@PathVariable Long developerId) {
        return taskService.getTasksByDeveloperId(developerId);
    }

    @PatchMapping("/{id}/status")
    public Task updateTaskStatus(@PathVariable Long id, @RequestParam com.agileai.agile_resource_optimizer.model.TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }
    @PostMapping("/sprint/{sprintId}/complete-all")
    public org.springframework.http.ResponseEntity<?> completeAllTasksInSprint(@PathVariable Long sprintId) {
        try {
            taskService.completeAllTasksInSprint(sprintId);
            return org.springframework.http.ResponseEntity.ok(java.util.Map.of("message", "All tasks marked as completed"));
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
