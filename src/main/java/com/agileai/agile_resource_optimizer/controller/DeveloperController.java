package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping
    public Developer addDeveloper(@RequestBody Developer dev) {
        return developerService.addDeveloper(dev);
    }

    @GetMapping
    public List<Developer> getAll() {
        return developerService.getAllDevelopers();
    }
}