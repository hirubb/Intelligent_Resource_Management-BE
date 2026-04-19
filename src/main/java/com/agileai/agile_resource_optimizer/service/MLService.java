package com.agileai.agile_resource_optimizer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MLService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double predict(Map<String, Object> features) {
        String url = "http://localhost:5000/predict";
        Map response = restTemplate.postForObject(url, features, Map.class);
        return Double.parseDouble(response.get("score").toString());
    }
}