package com.mainul35.socialmedium.services;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import com.mainul35.auth.dtos.UserLoginDto;
import com.mainul35.auth.models.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

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

    public Mono<UserAuthInfoDto> createAuthenticationInfo(UserAuthInfoDto userInfoRequest) {
        return webClient.post().uri(uriBuilder -> uriBuilder
                        .path("/user/create")
                        .build()
                )
                .bodyValue(userInfoRequest)
                .retrieve()
                .onStatus( 
                    HttpStatus.INTERNAL_SERVER_ERROR::equals,
                    response -> response.bodyToMono(String.class).map(RuntimeException::new)) 
                  .onStatus(
                    HttpStatus.BAD_REQUEST::equals,
                    response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                    HttpStatus.UNAUTHORIZED::equals,
                    response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                    HttpStatus.FORBIDDEN::equals,
                    response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .bodyToMono(UserAuthInfoDto.class);
    }

    public Mono<UserEntity> login(UserLoginDto userLoginDto) {
        return webClient.post().uri(uriBuilder -> uriBuilder
                        .path("/user/login")
                        .build()
                )
                .bodyValue(userLoginDto)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .bodyToMono(UserEntity.class);
    }

    public Mono<Boolean> validateToken(String authorizationHeader) {
        return webClient.post().uri(uriBuilder -> uriBuilder
                        .path("/token/validate")
                        .build()
                )
                .header("authorization", authorizationHeader)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                        HttpStatus.UNAUTHORIZED::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .bodyToMono(Boolean.class);
    }

}
