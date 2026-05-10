package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.model.Sprint;
import com.agileai.agile_resource_optimizer.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private BehaviorSyncService behaviorSyncService;

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id).orElseThrow(() -> new RuntimeException("Sprint not found"));
    }

    public Sprint completeSprint(Long id) {
        Sprint sprint = sprintRepository.findById(id).orElseThrow(() -> new RuntimeException("Sprint not found"));

        // Check if all tasks are completed
        boolean allTasksCompleted = sprint.getTasks().stream()
                .allMatch(task -> com.agileai.agile_resource_optimizer.model.TaskStatus.COMPLETED.equals(task.getStatus()));

        if (!allTasksCompleted) {
            throw new RuntimeException("Cannot complete sprint. Some tasks are still in progress.");
        }

        sprint.setStatus("COMPLETED");
        Sprint savedSprint = sprintRepository.save(sprint);

        // Update developer behavioral metrics (consistency, learning rate)
        try {
            behaviorSyncService.syncSprintMetrics(savedSprint);
        } catch (Exception e) {
            System.err.println("Warning: Failed to sync behavioral metrics: " + e.getMessage());
        }

        return savedSprint;
    }
}
