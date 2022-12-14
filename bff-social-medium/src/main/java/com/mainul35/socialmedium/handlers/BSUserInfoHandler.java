package com.mainul35.socialmedium.handlers;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import com.mainul35.socialmedium.dto.request.CreateUserRequest;
import com.mainul35.socialmedium.services.BSAuthService;
import com.mainul35.socialmedium.services.BSUserInfoService;
import controllers.dtos.request.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@AllArgsConstructor
@Slf4j
public class BSUserInfoHandler {

    private final BSUserInfoService bsUserInfoService;
    private final BSAuthService authService;

    public Mono<ServerResponse> requestConnectionHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var connectionId = request.pathVariables().get("connectionId");
        return bsUserInfoService
                .requestConnection(userId, connectionId)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> acceptConnectionHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var connectionId = request.pathVariables().get("connectionId");
        return bsUserInfoService
                .acceptConnection(userId, connectionId)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> rejectConnectionHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var connectionId = request.pathVariables().get("connectionId");
        return bsUserInfoService
                .rejectConnection(userId, connectionId)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> blockConnectionHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var connectionId = request.pathVariables().get("connectionId");
        return bsUserInfoService
                .blockConnection(userId, connectionId)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> unblockConnectionHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var connectionId = request.pathVariables().get("connectionId");
        return bsUserInfoService
                .unblockConnection(userId, connectionId)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> getConnectionRequestsHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var pageIdx = Integer.valueOf(request.queryParam("pageIdx").get());
        var itemsPerPage = Integer.valueOf(request.queryParam("itemsPerPage").get());

        return bsUserInfoService
                .getConnectionRequests(userId, pageIdx, itemsPerPage)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> getBlockedConnectionsHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var pageIdx = Integer.valueOf(request.queryParam("pageIdx").get());
        var itemsPerPage = Integer.valueOf(request.queryParam("itemsPerPage").get());

        return bsUserInfoService
                .getBlockedConnections(userId, pageIdx, itemsPerPage)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> getConnectedUsersHandler(ServerRequest request) {
        var userId = request.pathVariables().get("userId");
        var pageIdx = Integer.valueOf(request.queryParam("pageIdx").get());
        var itemsPerPage = Integer.valueOf(request.queryParam("itemsPerPage").get());

        return bsUserInfoService
                .getConnectedUsers(userId, pageIdx, itemsPerPage)
                .flatMap(userConnectionInfoResponse -> ServerResponse.ok().bodyValue(userConnectionInfoResponse));
    }

    public Mono<ServerResponse> getUsersHandler(ServerRequest request) {
        var pageIdx = Integer.valueOf(request.queryParam("pageIdx").get());
        var itemsPerPage = Integer.valueOf(request.queryParam("itemsPerPage").get());

        return bsUserInfoService
                .getUsers(pageIdx, itemsPerPage)
                .flatMap(infoResponseList -> ServerResponse.ok().bodyValue(infoResponseList));
    }

    public Mono<ServerResponse> createUserHandler(ServerRequest request) {
        var requestMono = request.bodyToMono(CreateUserRequest.class);
        return requestMono.flatMap(createUserRequest -> bsUserInfoService
                .createConnection(createUserRequest.userInfo())
                        // Perform force-register
                        // TODO: This code will be removed in stable version
                        .doOnError(throwable -> {
                            log.error(throwable.getMessage());
                            var userAuthInfoDto = new UserAuthInfoDto();
                            userAuthInfoDto.setUsername(createUserRequest.userInfo().getUsername());
                            userAuthInfoDto.setPassword(createUserRequest.authInfo().getPassword());
                            var authServerResp = authService.createAuthenticationInfo(userAuthInfoDto);
                            authServerResp.flatMap(authInfoDto -> ServerResponse.ok().build()).block(Duration.ofMillis(100));
                        })
                .map(resp -> {
                    var userAuthInfoDto = new UserAuthInfoDto();
                    userAuthInfoDto.setUsername(createUserRequest.userInfo().getUsername());
                    userAuthInfoDto.setPassword(createUserRequest.authInfo().getPassword());
                    var authServerResp = authService.createAuthenticationInfo(userAuthInfoDto);
                    return authServerResp.flatMap(authInfoDto -> ServerResponse.ok().build());
                }).flatMap(serverResponseMono -> serverResponseMono));
    }

    public Mono<ServerResponse> searchHandler(ServerRequest request) {
        var requestMono = request.bodyToMono(Filter.class);
        return requestMono.flatMap(requestObj -> bsUserInfoService
                .search(requestObj)
                .flatMap(resp -> ServerResponse.ok().bodyValue(resp)));
    }

    public Mono<ServerResponse> getUserProfileByIdHandler(ServerRequest request) {
        var userId = request.pathVariables().get("id");
        return bsUserInfoService
                .findProfile(userId)
                .flatMap(userInfo -> ServerResponse.ok().bodyValue(userInfo));
    }

    public Mono<ServerResponse> getNonConnectedUsersHandler(ServerRequest request) {
        var userId = request.pathVariables().get("id");
        var pageIdx = Integer.valueOf(request.queryParam("pageIdx").get());
        var itemsPerPage = Integer.valueOf(request.queryParam("itemsPerPage").get());
        return bsUserInfoService
                .getNonConnectedUsers(userId, pageIdx, itemsPerPage)
                .flatMap(nonConnectedUsers -> ServerResponse.ok().bodyValue(nonConnectedUsers));
    }
}
