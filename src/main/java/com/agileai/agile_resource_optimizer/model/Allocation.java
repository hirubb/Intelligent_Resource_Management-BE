package com.agileai.agile_resource_optimizer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allocations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // RELATIONSHIPS
    // =========================

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    // =========================
    // ML SCORES
    // =========================

    private Double mlScore;

    private Double skillMatchScore;

    private Double workloadBalance;

    private Double finalScore;

    private Integer rankPosition;

    private String status;

    @Column(length = 2000)
    private String explanation;
}
