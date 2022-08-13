package com.mainul35.socialmedium.services;

import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BSUserInfoService {

    private final WebClient webClient;

    public BSUserInfoService(@Value("${bsUserinfo.url}") String bsUserInfoServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(bsUserInfoServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public Mono<?> requestConnection(String userId, String connectionId) {

        return webClient.post().uri(uriBuilder -> uriBuilder.path("/{userId}/connections/request/{connectionId}").build(userId, connectionId))
                .retrieve()
                .bodyToMono(ParameterizedTypeReference.forType(UserConnectionInfoResponse.class));
    }

    public Mono<List<UserInfoResponse>> getUsers(Integer pageIdx, Integer itemsPerPage) {
        return webClient.get().uri(String.format("?pageIdx=%s&itemsPerPage=%s", pageIdx, itemsPerPage))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
            });
    }
}
