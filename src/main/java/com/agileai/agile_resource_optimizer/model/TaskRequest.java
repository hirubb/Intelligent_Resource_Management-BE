package com.agileai.agile_resource_optimizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaskRequest {
    @JsonProperty("task_id")
    private Long taskId;

    @JsonProperty("sprint_id")
    private String sprintId;

    private String task_type;
    private int task_complexity;
    private int story_points;
    private int req_frontend;
    private int req_backend;
    private int req_db;
}
