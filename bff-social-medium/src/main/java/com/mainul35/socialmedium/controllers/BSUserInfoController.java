package com.mainul35.socialmedium.controllers;

import com.mainul35.socialmedium.handlers.BSUserInfoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BSUserInfoController {

    private final BSUserInfoHandler handler;

    public BSUserInfoController(BSUserInfoHandler bsUserInfoHandler) {
        this.handler = bsUserInfoHandler;
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .POST("/userinfo/users/{userId}/connections/request/{connectionId}", handler::requestConnectionHandler)
                .GET("/userinfo/users/{userId}/connections/requests", handler::getConnectionRequestsHandler)
                .GET("/userinfo/users/{userId}/connections/blocked", handler::getBlockedConnectionsHandler)
                .GET("/userinfo/users/{userId}/connections", handler::getConnectedUsersHandler)
                .GET("/userinfo/users", handler::getUsersHandler)
                .GET("/userinfo/users/{id}/profile", handler::getUserProfileByIdHandler)
                .GET("/userinfo/users/{id}/non-connected-users", handler::getNonConnectedUsersHandler)
                .POST("/userinfo/users/create", handler::createUserHandler)
                .POST("/userinfo/users/search", handler::searchHandler)
                .PUT("/userinfo/users/{userId}/connections/accept/{connectionId}", handler::acceptConnectionHandler)
                .PUT("/userinfo/users/{userId}/connections/reject/{connectionId}", handler::rejectConnectionHandler)
                .PUT("/userinfo/users/{userId}/connections/block/{connectionId}", handler::blockConnectionHandler)
                .PUT("/userinfo/users/{userId}/connections/unblock/{connectionId}", handler::unblockConnectionHandler)
                .build();
    }
}
