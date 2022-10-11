package com.mainul35.socialmedium.handlers;

import com.mainul35.auth.dtos.UserLoginDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mainul35.socialmedium.services.BSAuthService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class BSAuthHandler {

    private final BSAuthService bsAuthService;
    public Mono<ServerResponse> loginHandler(ServerRequest request) {
        Mono<UserLoginDto> loginDtoMono = request.bodyToMono(UserLoginDto.class);
        // TODO: Implement Login handler
        return loginDtoMono.flatMap(loginInfo -> bsAuthService.login(loginInfo)
                .flatMap(userEntity ->
                        ServerResponse.ok().body(BodyInserters.fromValue(userEntity))
                ));
    }

    public Mono<ServerResponse> tokenValidationHandler(ServerRequest request) {
        var authorizationHeader = request.headers().firstHeader("authorization");
        return bsAuthService.validateToken(authorizationHeader).flatMap(aBoolean -> ServerResponse.ok().body(BodyInserters.fromValue(aBoolean)));
    }
}
