package com.agileai.agile_resource_optimizer.service;

import com.agileai.agile_resource_optimizer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BehaviorSyncService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeveloperService developerService;

    @Value("${python.api.url}")
    private String pythonApiUrl;

    public void syncBehaviorFromML(List<Map<String, Object>> sprintLogs) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("logs", sprintLogs);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //log it
        System.out.println("Sending payload to ML:");
        System.out.println(payload);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(payload, headers);

        String url = pythonApiUrl.replace("/predict", "/behavior/predict-all");

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> body = response.getBody();

        if (body == null || !body.containsKey("developers")) {
            return;
        }

        //log
        System.out.println("ML Response:");
System.out.println(body);

        List<Map<String, Object>> results =
                (List<Map<String, Object>>) body.get("developers");

        // 👉 delegate DB update to DeveloperService
        developerService.updateBehaviorMetrics(results);
    }
}