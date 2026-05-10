package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.model.Sprint;
import com.agileai.agile_resource_optimizer.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id).orElseThrow(() -> new RuntimeException("Sprint not found"));
    }
}
