package com.haydikodlayalim.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/account")
    public Mono<Map<String, Object>> accountFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SERVICE_UNAVAILABLE");
        response.put("message", "Account Service is currently taking too long to respond or is down. Please try again later.");
        response.put("timestamp", System.currentTimeMillis());
        return Mono.just(response);
    }

    @GetMapping("/ticket")
    public Mono<Map<String, Object>> ticketFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SERVICE_UNAVAILABLE");
        response.put("message", "Ticket Service is currently taking too long to respond or is down. Please try again later.");
        response.put("timestamp", System.currentTimeMillis());
        return Mono.just(response);
    }
}
