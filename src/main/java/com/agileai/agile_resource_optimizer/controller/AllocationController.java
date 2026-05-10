package com.agileai.agile_resource_optimizer.controller;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/sprint/{id}")
    public List<Allocation> getAllocationsBySprint(@PathVariable Long id) {
        return allocationService.getAllocationsBySprint(id);
    }
    
    @PutMapping("/assign")
    public Allocation assignDeveloper(
        @RequestParam Long allocationId,
        @RequestParam Long developerId) {

        return allocationService.assignRecommendedDeveloper(allocationId, developerId);
    }






    
}
