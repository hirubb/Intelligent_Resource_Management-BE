package com.agileai.agile_resource_optimizer.config;

import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.DeveloperProfile;
import com.agileai.agile_resource_optimizer.model.Sprint;
import com.agileai.agile_resource_optimizer.model.Task;
import com.agileai.agile_resource_optimizer.repository.DeveloperProfileRepository;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import com.agileai.agile_resource_optimizer.repository.SprintRepository;
import com.agileai.agile_resource_optimizer.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(DeveloperProfileRepository profileRepo, DeveloperRepository devRepo, TaskRepository taskRepo, SprintRepository sprintRepo) {
        return args -> {
            seedDevelopers(profileRepo, devRepo);
            List<Sprint> sprints = seedSprints(sprintRepo);
            seedTasks(taskRepo, sprints);
        };
    }

    private void seedDevelopers(DeveloperProfileRepository profileRepo, DeveloperRepository devRepo) {
        if (devRepo.count() > 0) {
            System.out.println("Developers already exist. Skipping seeding...");
            return;
        }

        Random rand = new Random();

        for (int i = 1; i <= 30; i++) {
            Developer dev = new Developer();
            dev.setDev_id("D" + i);
            int exp = rand.nextInt(3) + 1;
            dev.setExperience_level(exp);
            dev.setSkill_frontend(rand.nextInt(5) + 1);
            dev.setSkill_backend(rand.nextInt(5) + 1);
            dev.setSkill_db(rand.nextInt(5) + 1);
            dev.setConsistency(0.6 + (1.0 - 0.6) * rand.nextDouble());
            dev.setLearning_rate(0.01 + (0.1 - 0.01) * rand.nextDouble());
            int tasks = rand.nextInt(6) + 1;
            dev.setCurrent_tasks(tasks);
            int workload = tasks * (rand.nextInt(6) + 3);
            dev.setCurrent_workload(workload);
            double availability = Math.max(0, 100 - workload * (3 + rand.nextDouble() * 3));
            dev.setAvailability(availability);

            DeveloperProfile profile = new DeveloperProfile();
            profile.setFirstName("Developer");
            profile.setLastName(String.valueOf(i));
            profile.setAge(rand.nextInt(35) + 22); // Age 22 to 56
            profile.setEmail("dev" + i + "@agileai.com");
            profile.setPhoneNumber("123-456-78" + String.format("%02d", i));

            profile.setDeveloperMetrics(dev);
            dev.setProfile(profile);

            profileRepo.save(profile);
        }

        System.out.println("✅ 30 Developers inserted into database!");
    }

    private List<Sprint> seedSprints(SprintRepository repo) {
        if (repo.count() > 0) {
            System.out.println("Sprints already exist. Skipping seeding...");
            return repo.findAll();
        }

        List<Sprint> sprints = new ArrayList<>();
        
        sprints.add(repo.save(Sprint.builder()
                .sprintId("S-2024-01")
                .startDate(LocalDate.now().minusDays(14))
                .endDate(LocalDate.now())
                .status("COMPLETED")
                .build()));

        sprints.add(repo.save(Sprint.builder()
                .sprintId("S-2024-02")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(14))
                .status("ACTIVE")
                .build()));

        sprints.add(repo.save(Sprint.builder()
                .sprintId("S-2024-03")
                .startDate(LocalDate.now().plusDays(14))
                .endDate(LocalDate.now().plusDays(28))
                .status("PLANNED")
                .build()));

        System.out.println("✅ 3 Sprints inserted into database!");
        return sprints;
    }

    private void seedTasks(TaskRepository repo, List<Sprint> sprints) {
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
            task.setAmbiguity(rand.nextDouble());
            task.setDependency_risk(rand.nextDouble());
            
            // Assign a random sprint
            if (!sprints.isEmpty()) {
                task.setSprint(sprints.get(rand.nextInt(sprints.size())));
            }
            
            repo.save(task);
        }

        System.out.println("✅ " + taskData.length + " Realistic Tasks inserted and linked to Sprints!");
    }

}