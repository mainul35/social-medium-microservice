package com.mainul35.socialmedium.handlers;

import com.mainul35.socialmedium.dto.request.LoginRequest;
import com.mainul35.socialmedium.services.BSAuthService;
import com.mainul35.socialmedium.services.BSUserInfoService;
import controllers.dtos.request.UserInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class BSAuthHandler {

    private final BSAuthService bsAuthService;
    public Mono<ServerResponse> loginHandler(ServerRequest request) {
        Mono<LoginRequest> loginRequest = request.bodyToMono(LoginRequest.class);
        // TODO: Implement Login handler
//        loginRequest.flatMap(loginInfo -> {
//            var userInfoMono = bsUserInfoService.findProfile(loginInfo.getUsername());
//            userInfoMono.flatMap(userInfoResponse -> {
//                userInfoResponse.getUsername()
//            })
//
//        })
        return null;
    }
}
