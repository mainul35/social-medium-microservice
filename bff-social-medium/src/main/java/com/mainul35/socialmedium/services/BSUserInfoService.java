package com.mainul35.socialmedium.services;

import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.List;

@Service
@Validated
public class BSUserInfoService {

    private final WebClient webClient;

    public BSUserInfoService(@Value("${bsUserinfo.url}") String bsUserInfoServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(bsUserInfoServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public Mono<UserConnectionInfoResponse> requestConnection(String userId, String connectionId) {

        return webClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path(String.format("/%s/connections/request/%s", userId, connectionId))
                        .build()
                )
                .retrieve()
                .bodyToMono(ParameterizedTypeReference.forType(UserConnectionInfoResponse.class));
    }

    public Mono<List<UserInfoResponse>> getUsers(Integer pageIdx, Integer itemsPerPage) {
       return webClient.get().uri(uriBuilder -> uriBuilder
                .path("/")
               .queryParam("pageIdx", pageIdx)
               .queryParam("itemsPerPage", itemsPerPage)
               .build()
       )
               .retrieve()
               .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<UserConnectionInfoResponse> acceptConnection(String userId, String connectionId) {
        return webClient.put().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections/accept/%s", userId, connectionId))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<UserConnectionInfoResponse> rejectConnection(String userId, String connectionId) {
        return webClient.put().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections/reject/%s", userId, connectionId))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<UserConnectionInfoResponse> blockConnection(String userId, String connectionId) {
        return webClient.put().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections/block/%s", userId, connectionId))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<UserConnectionInfoResponse> unblockConnection(String userId, String connectionId) {
        return webClient.put().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections/unblock/%s", userId, connectionId))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<List<UserConnectionInfoResponse>> getConnectionRequests(String userId, Integer pageIdx, Integer itemsPerPage) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections/requests", userId))
                        .queryParam("pageIdx", pageIdx)
                        .queryParam("itemsPerPage", itemsPerPage)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }


    public Mono<List<UserConnectionInfoResponse>> getBlockedConnections(String userId, Integer pageIdx, Integer itemsPerPage) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections/blocked", userId))
                        .queryParam("pageIdx", pageIdx)
                        .queryParam("itemsPerPage", itemsPerPage)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<List<UserConnectionInfoResponse>> getConnectedUsers(String userId, Integer pageIdx, Integer itemsPerPage) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/connections", userId))
                        .queryParam("pageIdx", pageIdx)
                        .queryParam("itemsPerPage", itemsPerPage)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<String> createConnection(UserInfoRequest userInfoRequest) {
        return webClient.post().uri(uriBuilder -> uriBuilder
                        .path("/create")
                        .build()
                )
                .bodyValue(userInfoRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<List<UserInfoResponse>> search(@Valid Filter filter) {
        return webClient.post().uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .build()
                )
                .bodyValue(filter)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserInfoResponse>>() {});
    }

    public Mono<UserInfoResponse> findProfile(String id) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/profile", id))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<UserInfoResponse>() {});
    }

    public Mono<List<UserConnectionInfoResponse>> getNonConnectedUsers(String id, Integer pageIdx, Integer itemsPerPage) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/non-connected-users", id))
                        .queryParam("pageIdx", pageIdx)
                        .queryParam("itemsPerPage", itemsPerPage)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserConnectionInfoResponse>>() {});
    }
}
