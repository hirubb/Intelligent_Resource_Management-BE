package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.entity.Developer;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository repo;

    public List<Developer> getAll() {
        return repo.findAll();
    }

    public Developer save(Developer dev) {
        return repo.save(dev);
    }
}