package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.model.Developer;
import com.agileai.agile_resource_optimizer.model.TaskRequest;
import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankingService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${python.api.url}")
    private String pythonApiUrl;

    public String rankDevelopers(TaskRequest task) {

        // GET DEVELOPERS FROM POSTGRESQL
        List<Developer> developers = developerRepository.findAll();

        // BUILD FULL PAYLOAD
        Map<String, Object> payload = new HashMap<>();
        payload.put("task", task);
        payload.put("developers", developers);

        System.out.println("PAYLOAD:");
        System.out.println(payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                pythonApiUrl,
                request,
                String.class
        );

        return response.getBody();
    }
}