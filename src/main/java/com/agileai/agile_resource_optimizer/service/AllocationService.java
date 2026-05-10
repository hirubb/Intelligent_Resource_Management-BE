package com.agileai.agile_resource_optimizer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agileai.agile_resource_optimizer.model.*;

import com.agileai.agile_resource_optimizer.repository.AllocationRepository;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import com.agileai.agile_resource_optimizer.repository.TaskRepository;


@Service
public class AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<Allocation> getAllocationsBySprint(Long sprintId) {
        return allocationRepository.findBySprint_Id(sprintId);
    }

    /**
     * Returns ALL allocations (including DECLINED) for a task.
     * Frontend can filter to show only RECOMMENDED / ALLOCATED ones.
     */
    public List<Allocation> getTaskAllocations(Long taskId) {
        return allocationRepository.findByTask_Id(taskId);
    }

    public Optional<Allocation> updateAllocation(Long allocationId, Allocation allocationDetails) {
        return allocationRepository.findById(allocationId).map(allocation -> {
            allocation.setStatus(allocationDetails.getStatus());
            return allocationRepository.save(allocation);
        });
    }

    /**
     * Approve a specific developer for a task:
     *   1. Set the chosen allocation -> ALLOCATED
     *   2. Set all OTHER RECOMMENDED allocations for the SAME task -> DECLINED
     *      (the other 4 competing candidates for this specific task lose their slot)
     *   3. Update the developer's real workload metrics in the DB so that
     *      the NEXT call to /rank-sprint sends accurate data to the ML model,
     *      which will naturally deprioritize this developer via the workload_balance
     *      (deviation-from-team-mean) score. No hard cross-task blocking is done —
     *      the admin can still assign this developer to other tasks if needed.
     *   4. Persist developer onto the Task record
     */
    public Allocation assignRecommendedDeveloper(Long allocationId, Long developerId) {

        Allocation allocation = allocationRepository.findById(allocationId)
            .orElseThrow(() -> new RuntimeException("Allocation not found"));

        Developer developer = developerRepository.findById(developerId)
            .orElseThrow(() -> new RuntimeException("Developer not found"));

        Task task = allocation.getTask();

        // 1. Assign chosen developer to this allocation
        allocation.setDeveloper(developer);
        allocation.setStatus("ALLOCATED");

        // 2. Decline ONLY the other RECOMMENDED allocations for THIS same task
        //    (e.g. D2, D3, D4, D5 who also competed for T1 lose their T1 slot)
        //    D1's RECOMMENDED entries on T2, T3, T4 are intentionally left untouched —
        //    the admin may still approve D1 for other tasks.
        List<Allocation> siblingAllocations = allocationRepository.findByTask_Id(task.getId());
        for (Allocation sibling : siblingAllocations) {
            if (!sibling.getId().equals(allocationId) && "RECOMMENDED".equals(sibling.getStatus())) {
                sibling.setStatus("DECLINED");
                allocationRepository.save(sibling);
            }
        }

        // 3. Assign developer to the task entity and set status to PLANNED
        task.setDeveloper(developer);
        task.setStatus(com.agileai.agile_resource_optimizer.model.TaskStatus.PLANNED);
        taskRepository.save(task);

        // 4. Update developer's workload metrics so the ML model receives
        //    accurate data in future /rank-sprint calls and naturally penalises
        //    this developer's workload_balance score for subsequent tasks.
        //    - current_tasks   : +1
        //    - current_workload: += story_points  (reflects task weight, not just count)
        //    - availability    : decreases by 5% per story point (min 0)
        int storyPoints = task.getStory_points();
        developer.setCurrent_tasks(developer.getCurrent_tasks() + 1);
        developer.setCurrent_workload(developer.getCurrent_workload() + storyPoints);
        double newAvailability = Math.max(0.0, developer.getAvailability() - (storyPoints * 5.0));
        developer.setAvailability(newAvailability);
        developerRepository.save(developer);

        // 5. Persist and return the approved allocation
        return allocationRepository.save(allocation);
    }

    /**
     * Manually decline a single recommended developer for a task.
     */
    public Allocation declineAllocation(Long allocationId) {
        Allocation allocation = allocationRepository.findById(allocationId)
            .orElseThrow(() -> new RuntimeException("Allocation not found"));

        if ("ALLOCATED".equals(allocation.getStatus())) {
            throw new RuntimeException("Cannot decline an already-approved allocation.");
        }

        allocation.setStatus("DECLINED");
        return allocationRepository.save(allocation);
    }

    /**
     * Approve all top-ranked (Rank 1) recommendations for all pending tasks in a sprint.
     */
    public void approveAllRecommendations(Long sprintId) {
        List<Allocation> allAllocations = allocationRepository.findBySprint_Id(sprintId);

        // Group by task to identify pending tasks
        java.util.Map<Long, List<Allocation>> taskGroups = allAllocations.stream()
            .collect(java.util.stream.Collectors.groupingBy(a -> a.getTask().getId()));

        for (java.util.Map.Entry<Long, List<Allocation>> entry : taskGroups.entrySet()) {
            List<Allocation> allocations = entry.getValue();

            // Skip if any developer is already ALLOCATED for this task
            boolean alreadyAllocated = allocations.stream()
                .anyMatch(a -> "ALLOCATED".equals(a.getStatus()));

            if (!alreadyAllocated) {
                // Find the Rank 1 recommendation
                allocations.stream()
                    .filter(a -> a.getRankPosition() == 1 && "RECOMMENDED".equals(a.getStatus()))
                    .findFirst()
                    .ifPresent(topAlloc -> {
                        assignRecommendedDeveloper(topAlloc.getId(), topAlloc.getDeveloper().getId());
                    });
            }
        }
    }
}
