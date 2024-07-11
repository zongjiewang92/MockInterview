package com.fdu.mockinterview.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/interview")
public class InterviewerStateMachineController {
    private final WebClient webClient;

    public InterviewerStateMachineController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
    }

    @PostMapping("/initialize")
    public Mono<Map> initialize(@RequestBody Map<String, Object> payload) {
        return webClient.post()
                .uri("/initialize")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class);
    }

    @PostMapping("/service")
    public Mono<Map> evaluate(@RequestBody Map<String, Object> payload) {
        return webClient.post()
                .uri("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class);
    }
}
