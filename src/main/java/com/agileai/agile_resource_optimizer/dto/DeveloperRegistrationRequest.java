package com.agileai.agile_resource_optimizer.dto;

import lombok.Data;

@Data
public class DeveloperRegistrationRequest {
    // Personal Details
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String specialization;
    private String bio;

    // ML / Optimization Metrics
    private String devId;
    private int experienceLevel;
    private int skillFrontend;
    private int skillBackend;
    private int skillDb;
    private int currentWorkload;
    private double availability;
    private int currentTasks;
    private double consistency;
    private double learningRate;
}
