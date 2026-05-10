package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.model.Task;
import com.agileai.agile_resource_optimizer.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksBySprintId(Long sprintId) {
        return taskRepository.findBySprintId(sprintId);
    }

    public List<Task> getTasksByDeveloperId(Long developerId) {
        return taskRepository.findByDeveloperId(developerId);
    }

    // =====================================================
    // BUILD ML SPRINT LOGS
    // =====================================================


    public List<Map<String, Object>> buildSprintLogs() {

    List<Task> tasks = taskRepository.findAll();

    List<Map<String, Object>> logs = new ArrayList<>();

    for (Task task : tasks) {

        Map<String, Object> row = new HashMap<>();

        row.put(
            "dev_id",
            task.getDeveloper().getDev_id()
        );

        row.put(
            "sprint_id",
            task.getSprint().getId()
        );

        row.put(
            "completion_time",
            task.getCompletion_time()
        );

        row.put(
            "defects",
            task.getDefects()
        );

        row.put(
            "velocity_contribution",
            task.getVelocity_contribution()
        );

        row.put(
            "blocked_tasks",
            task.isBlocked() ? 1 : 0
        );

        row.put(
            "reopened_tasks",
            task.isReopened() ? 1 : 0
        );

        logs.add(row);
    }

    return logs;
}

    public Task updateTaskStatus(Long id, com.agileai.agile_resource_optimizer.model.TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        if (status == com.agileai.agile_resource_optimizer.model.TaskStatus.COMPLETED) {
            task.setCompleted_at(java.time.LocalDateTime.now());
        } else if (status == com.agileai.agile_resource_optimizer.model.TaskStatus.IN_PROGRESS && task.getStarted_at() == null) {
            task.setStarted_at(java.time.LocalDateTime.now());
        }
        return taskRepository.save(task);
    }
}
