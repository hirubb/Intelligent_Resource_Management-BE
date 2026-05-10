package com.agileai.agile_resource_optimizer.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "developers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dev_id;
    private int experience_level;
    private int skill_frontend;
    private int skill_backend;
    private int skill_db;
    private int current_workload;
    private double availability;
    private int current_tasks;
    private double consistency;
    private double learning_rate;

    @OneToOne(mappedBy = "developerMetrics")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("developerMetrics")
    private DeveloperProfile profile;

}
