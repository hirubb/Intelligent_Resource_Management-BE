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

    public Optional<Allocation> updateAllocation(Long allocationId, Allocation allocationDetails) {
        return allocationRepository.findById(allocationId).map(allocation -> {
            allocation.setStatus(allocationDetails.getStatus());
            return allocationRepository.save(allocation);
        });
    }

    public Allocation assignRecommendedDeveloper(Long allocationId, Long developerId) {

        Allocation allocation = allocationRepository.findById(allocationId)
            .orElseThrow(() -> new RuntimeException("Allocation not found"));

        Developer developer = developerRepository.findById(developerId)
            .orElseThrow(() -> new RuntimeException("Developer not found"));

        Task task = allocation.getTask();

        // 1. assign developer to allocation
        allocation.setDeveloper(developer);

        // 2. update status
        allocation.setStatus("ALLOCATED");

        // 3. assign developer to task
        task.setDeveloper(developer);

        taskRepository.save(task);

        // 4. save allocation
        return allocationRepository.save(allocation);
}



    

    
}
