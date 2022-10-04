package com.mainul35.socialmedium.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Validated
public class BSAuthService {

    private final WebClient webClient;

    public BSAuthService(@Value("${bsAuth.url}") String bsAuthServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(bsAuthServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }


}
