package com.agileai.agile_resource_optimizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String task_type;
    private int task_complexity;
    private int story_points;
    private int req_frontend;
    private int req_backend;
    private int req_db;
    private double ambiguity;
    private double dependency_risk;
}
