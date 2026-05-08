package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    public Developer addDeveloper(Developer dev) {
        return developerRepository.save(dev);
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }
}
