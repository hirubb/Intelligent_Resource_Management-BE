package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.entity.Developer;
import com.agileai.agile_resource_optimizer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired
    private DeveloperService service;

    @GetMapping
    public List<Developer> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Developer create(@RequestBody Developer dev) {
        return service.save(dev);
    }
}