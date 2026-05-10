package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.model.TaskRequest;
import com.agileai.agile_resource_optimizer.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.agileai.agile_resource_optimizer.service.BehaviorSyncService;
import com.agileai.agile_resource_optimizer.service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rank")
@CrossOrigin("*")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private BehaviorSyncService behaviorSyncService;

    @Autowired
    private TaskService taskService;

    @PostMapping
    public List<Map<String, Object>> rankDevelopers(@RequestBody TaskRequest taskRequest) {
        return rankingService.rankDevelopers(taskRequest);
    }

    @PostMapping("/sprint/{id}")
    public List<Map<String, Object>> allocateSprint(@PathVariable Long id) {
        return rankingService.allocateSprint(id);
    }

    @PostMapping("/recommendations/sprint/{id}")
    public List<Map<String, Object>> getSprintRecommendations(@PathVariable Long id) {
        return rankingService.getSprintRecommendations(id);
    }

    @PostMapping("/explain/{allocationId}")
    public Map<String, Object> explainAllocation(@PathVariable Long allocationId) {
        return rankingService.explainAllocation(allocationId);
    }


    @PostMapping("/sync-behavior")
    public String syncBehavior() {

    List<Map<String, Object>> logs =
            taskService.buildSprintLogs();

            behaviorSyncService.syncBehaviorFromML(logs);

    return "Behavior sync completed";
    }

    
}