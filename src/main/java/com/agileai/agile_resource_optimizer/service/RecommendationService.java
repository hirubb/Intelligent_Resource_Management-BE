package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.entity.Developer;
import com.agileai.agile_resource_optimizer.entity.Task;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    @Autowired
    private DeveloperRepository devRepo;

    @Autowired
    private MLService mlService;

    public List<Map<String, Object>> recommend(Task task) {

        List<Developer> devs = devRepo.findAll();
        List<Map<String, Object>> results = new ArrayList<>();

        for (Developer d : devs) {

            Map<String, Object> features = new HashMap<>();
            features.put("developer_skill_score", 0.7);
            features.put("experience_years", d.getExperienceYears());
            features.put("story_points", task.getStoryPoints());
            features.put("task_complexity", task.getComplexity());

            double score = mlService.predict(features);

            Map<String, Object> res = new HashMap<>();
            res.put("developerId", d.getId());
            res.put("score", score);

            results.add(res);
        }

        return results.stream()
                .sorted((a, b) -> Double.compare((double)b.get("score"), (double)a.get("score")))
                .toList();
    }
}