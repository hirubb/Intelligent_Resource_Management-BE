package com.agileai.agile_resource_optimizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sprints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sprintId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<Allocation> allocations;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
