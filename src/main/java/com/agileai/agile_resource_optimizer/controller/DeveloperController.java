package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.dto.DeveloperRegistrationRequest;
import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.DeveloperProfile;
import com.agileai.agile_resource_optimizer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
@CrossOrigin("*")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping
    public DeveloperProfile addDeveloper(@RequestBody DeveloperRegistrationRequest request) {
        return developerService.addDeveloper(request);
    }

    @GetMapping
    public List<Developer> getAll() {
        return developerService.getAllDevelopers();
    }

    @GetMapping("/profiles")
    public List<DeveloperProfile> getAllProfiles() {
        return developerService.getAllDeveloperProfiles();
    }

    
}