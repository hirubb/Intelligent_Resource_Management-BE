package com.agileai.agile_resource_optimizer.controller;

import com.agileai.agile_resource_optimizer.model.TaskRequest;
import com.agileai.agile_resource_optimizer.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rank")
@CrossOrigin("*")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @PostMapping
    public String rankDevelopers(@RequestBody TaskRequest taskRequest) {

        return rankingService.rankDevelopers(taskRequest);
    }
}