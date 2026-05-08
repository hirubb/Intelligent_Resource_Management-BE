package com.agileai.agile_resource_optimizer.config;

import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.Task;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import com.agileai.agile_resource_optimizer.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(DeveloperRepository devRepo, TaskRepository taskRepo) {
        return args -> {
            seedDevelopers(devRepo);
            seedTasks(taskRepo);
        };
    }

    private void seedDevelopers(DeveloperRepository repo) {
        // Avoid duplicate insert
        if (repo.count() > 0) {
            System.out.println("Developers already exist. Skipping seeding...");
            return;
        }

        Random rand = new Random();

        for (int i = 1; i <= 30; i++) { // Increased to 30 as per Python script
            Developer dev = new Developer();
            dev.setDev_id("D" + i);

            // Experience: 1, 2, or 3
            int exp = rand.nextInt(3) + 1;
            dev.setExperience_level(exp);

            // Skills: 1 to 5
            dev.setSkill_frontend(rand.nextInt(5) + 1);
            dev.setSkill_backend(rand.nextInt(5) + 1);
            dev.setSkill_db(rand.nextInt(5) + 1);

            // Consistency: 0.6 to 1.0
            dev.setConsistency(0.6 + (1.0 - 0.6) * rand.nextDouble());

            // Learning Rate: 0.01 to 0.1
            dev.setLearning_rate(0.01 + (0.1 - 0.01) * rand.nextDouble());

            // Workload & Tasks
            int tasks = rand.nextInt(6) + 1;
            dev.setCurrent_tasks(tasks);
            int workload = tasks * (rand.nextInt(6) + 3); // 3 to 8
            dev.setCurrent_workload(workload);

            // Availability: max(0, 100 - workload * rand(3, 6))
            double availability = Math.max(0, 100 - workload * (3 + rand.nextDouble() * 3));
            dev.setAvailability(availability);

            repo.save(dev);
        }

        System.out.println("✅ 30 Developers inserted into database (aligned with training data)!");
    }

    private void seedTasks(TaskRepository repo) {
        // Avoid duplicate insert
        if (repo.count() > 0) {
            System.out.println("Tasks already exist. Skipping seeding...");
            return;
        }

        Random rand = new Random();
        String[][] taskData = {
            {"Fix Login Authentication Bug", "4", "5", "1", "5", "3"},
            {"Implement Dashboard Charts", "3", "8", "5", "2", "1"},
            {"Optimize Database Queries for Reports", "5", "13", "0", "3", "5"},
            {"Develop User Profile API", "2", "3", "1", "4", "2"},
            {"Design Responsive Landing Page", "2", "5", "5", "0", "0"},
            {"Integrate Stripe Payment Gateway", "5", "21", "2", "5", "4"},
            {"Build Automated PDF Report Generator", "3", "8", "1", "5", "2"},
            {"Implement Real-time WebSocket Notifications", "4", "13", "4", "5", "1"},
            {"Refactor Legacy Codebase Module", "4", "13", "2", "4", "2"},
            {"Setup CI/CD Pipeline for Microservices", "5", "21", "1", "5", "3"},
            {"Fix UI Consistency Issues in Admin Panel", "2", "3", "5", "1", "1"},
            {"Add Multi-factor Authentication Support", "4", "8", "2", "5", "4"}
        };

        for (String[] data : taskData) {
            Task task = new Task();
            task.setTask_type(data[0]);
            task.setTask_complexity(Integer.parseInt(data[1]));
            task.setStory_points(Integer.parseInt(data[2]));
            task.setReq_frontend(Integer.parseInt(data[3]));
            task.setReq_backend(Integer.parseInt(data[4]));
            task.setReq_db(Integer.parseInt(data[5]));
            
            // New fields from Python script
            task.setAmbiguity(rand.nextDouble()); // 0.0 to 1.0
            task.setDependency_risk(rand.nextDouble()); // 0.0 to 1.0
            
            repo.save(task);
        }

        System.out.println("✅ " + taskData.length + " Realistic Tasks inserted into database!");
    }

}