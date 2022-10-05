package com.mainul35.socialmedium.handlers;

import com.mainul35.socialmedium.services.BSAuthService;
import com.mainul35.socialmedium.services.BSUserInfoService;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import lombok.AllArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
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
        var requestMono = request.bodyToMono(UserInfoRequest.class);
        return requestMono.flatMap(userInfoRequest -> bsUserInfoService
                .createConnection(userInfoRequest).doOnNext(resp -> {
                    // authService
                });

                // .flatMap(resp -> ServerResponse.ok().build()));
        // TODO: After user basic info creation, create authentication and authorization info
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
