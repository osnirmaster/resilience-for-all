package br.com.master.stack.resilience.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/resilience")
public class ResilienceController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:1080/";

    @GetMapping("/circuit-breaker")
    public String getCallCircuitBreaker(){
        return restTemplate.getForObject(
                BASE_URL + "/view/cart",
                String.class);
    }
}
