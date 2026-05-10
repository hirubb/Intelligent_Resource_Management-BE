package com.agileai.agile_resource_optimizer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.agileai.agile_resource_optimizer.model.*;
import com.agileai.agile_resource_optimizer.service.AllocationService;

@RestController
@RequestMapping("/api/allocation")
@CrossOrigin("*")
public class AllocationController {

    @Autowired
    private AllocationService allocationService;
    
    @GetMapping("/{id}")
    public Allocation getAllocationById(@PathVariable Long id) {
        return allocationService.getAllocationById(id);
    }

    /** All allocations for a sprint (used by the main AI-Allocation list page). */
    @GetMapping("/sprint/{id}")
    public List<Allocation> getAllocationsBySprint(@PathVariable Long id) {
        return allocationService.getAllocationsBySprint(id);
    }

    /**
     * All allocations (all ranks) for a specific task.
     * Used by the frontend to show the top-5 recommended developers per task.
     */
    @GetMapping("/task/{taskId}")
    public List<Allocation> getAllocationsByTask(@PathVariable Long taskId) {
        return allocationService.getTaskAllocations(taskId);
    }

    /**
     * Approve a specific developer for a task.
     * Sets chosen allocation -> ALLOCATED, demotes others -> DECLINED.
     */
    @PutMapping("/assign")
    public Allocation assignDeveloper(
        @RequestParam Long allocationId,
        @RequestParam Long developerId) {

        return allocationService.assignRecommendedDeveloper(allocationId, developerId);
    }

    /**
     * Decline a specific recommended developer for a task.
     */
    @PutMapping("/{allocationId}/decline")
    public Allocation declineAllocation(@PathVariable Long allocationId) {
        return allocationService.declineAllocation(allocationId);
    }

    /**
     * Approve all top-ranked recommendations for a sprint.
     */
    @PostMapping("/approve-all/sprint/{sprintId}")
    public void approveAllRecommendations(@PathVariable Long sprintId) {
        allocationService.approveAllRecommendations(sprintId);
    }
}
