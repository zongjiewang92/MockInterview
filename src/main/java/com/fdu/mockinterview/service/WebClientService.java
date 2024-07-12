package com.fdu.mockinterview.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("webClientService")
public class WebClientService {

    private static final WebClient webClient = WebClient.create("http://localhost:5000");

    public WebClient getWebClient() {
        return webClient;
    }

}
