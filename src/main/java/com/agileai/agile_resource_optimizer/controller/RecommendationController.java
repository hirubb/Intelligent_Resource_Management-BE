package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.entity.Task;
import com.agileai.agile_resource_optimizer.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService service;

    @PostMapping
    public List<Map<String, Object>> recommend(@RequestBody Task task) {
        return service.recommend(task);
    }
}