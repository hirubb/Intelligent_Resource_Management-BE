package com.agileai.agile_resource_optimizer.model;

import java.time.LocalDateTime;

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

    // =====================================================
    // RELATIONSHIPS
    // =====================================================

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    // =====================================================
    // TASK DETAILS
    // =====================================================

    private String task_type;
    private int task_complexity;
    private int story_points;

    // =====================================================
    // REQUIRED SKILLS
    // =====================================================

    private int req_frontend;
    private int req_backend;
    private int req_db;

    // =====================================================
    // RISK FACTORS
    // =====================================================

    private double ambiguity;
    private double dependency_risk;

    // =====================================================
    // TASK EXECUTION METRICS
    // (USED FOR ML / BEHAVIORAL ANALYSIS)
    // =====================================================


    private double completion_time;
    private int defects;
    private double velocity_contribution;
    private boolean blocked;
    private boolean reopened;

    // =====================================================
    // TASK STATUS
    // =====================================================

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    // =====================================================
    // TIMESTAMPS
    // =====================================================

    private LocalDateTime created_at;
    private LocalDateTime started_at;
    private LocalDateTime completed_at;

}
