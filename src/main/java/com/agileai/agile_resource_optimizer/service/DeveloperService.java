package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.dto.DeveloperRegistrationRequest;
import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.DeveloperProfile;
import com.agileai.agile_resource_optimizer.repository.DeveloperProfileRepository;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private DeveloperProfileRepository developerProfileRepository;

    public DeveloperProfile addDeveloper(DeveloperRegistrationRequest request) {
        Developer developer = new Developer();
        developer.setDev_id(request.getDevId());
        developer.setExperience_level(request.getExperienceLevel());
        developer.setSkill_frontend(request.getSkillFrontend());
        developer.setSkill_backend(request.getSkillBackend());
        developer.setSkill_db(request.getSkillDb());
        developer.setCurrent_workload(request.getCurrentWorkload());
        developer.setAvailability(request.getAvailability());
        developer.setCurrent_tasks(request.getCurrentTasks());
        developer.setConsistency(request.getConsistency());
        developer.setLearning_rate(request.getLearningRate());

        DeveloperProfile profile = new DeveloperProfile();
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setAge(request.getAge());
        profile.setEmail(request.getEmail());
        profile.setPhoneNumber(request.getPhoneNumber());

        profile.setDeveloperMetrics(developer); // Link them
        developer.setProfile(profile);

        return developerProfileRepository.save(profile);
    }

    public List<DeveloperProfile> getAllDeveloperProfiles() {
        return developerProfileRepository.findAll();
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }
}
