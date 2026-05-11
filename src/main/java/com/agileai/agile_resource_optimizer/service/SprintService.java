package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.model.Sprint;
import com.agileai.agile_resource_optimizer.model.Task;
import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.TaskStatus;
import com.agileai.agile_resource_optimizer.repository.SprintRepository;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private BehaviorSyncService behaviorSyncService;

    @Autowired
    private DeveloperRepository developerRepository;

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
                .allMatch(task -> TaskStatus.COMPLETED.equals(task.getStatus()));

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

        // Clear/Reduce developer workload and task count
        updateDeveloperWorkloads(savedSprint);

        return savedSprint;
    }

    private void updateDeveloperWorkloads(Sprint sprint) {
        for (Task task : sprint.getTasks()) {
            if (task.getDeveloper() != null) {
                Developer dev = task.getDeveloper();
                
                // Subtract task story points from workload and decrement task count
                int newWorkload = Math.max(0, dev.getCurrent_workload() - task.getStory_points());
                int newTaskCount = Math.max(0, dev.getCurrent_tasks() - 1);
                
                dev.setCurrent_workload(newWorkload);
                dev.setCurrent_tasks(newTaskCount);
                
                developerRepository.save(dev);
            }
        }
    }
}
