package com.mainul35.socialmedium.controllers;

import com.mainul35.socialmedium.handlers.BSUserInfoHandler;
import com.mainul35.socialmedium.services.BSUserInfoService;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
//@RestController
//@RequestMapping("/userinfo/users")
public class BSUserInfoController {

    private final BSUserInfoHandler handler;

//    @Autowired
//    private BSUserInfoService bsUserInfoService;

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


    // @RestController based implementations

/*    @PostMapping("/{userId}/connections/request/{connectionId}")
    public Mono<UserConnectionInfoResponse> requestConnection(@PathVariable String userId, @PathVariable String connectionId) {
        return bsUserInfoService.requestConnection(userId, connectionId);
    }


    @PutMapping("/{userId}/connections/accept/{connectionId}")
    public Mono<?> acceptConnection(@PathVariable String userId, @PathVariable String connectionId) {
        return bsUserInfoService.acceptConnection(userId, connectionId);
    }

    @PutMapping("/{userId}/connections/reject/{connectionId}")
    public Mono<?> rejectConnection(@PathVariable String userId, @PathVariable String connectionId) {
        return bsUserInfoService.rejectConnection(userId, connectionId);
    }

    @PutMapping("/{userId}/connections/block/{connectionId}")
    public Mono<?> blockConnection(@PathVariable String userId, @PathVariable String connectionId) {
        return bsUserInfoService.blockConnection(userId, connectionId);
    }

    @PutMapping("/{userId}/connections/unblock/{connectionId}")
    public Mono<?> unblockConnection(@PathVariable String userId, @PathVariable String connectionId) {
        return bsUserInfoService.unblockConnection(userId, connectionId);
    }

    @GetMapping("/{userId}/connections/requests")
    public Mono<List<UserConnectionInfoResponse>> getConnectionRequests(@PathVariable String userId, Integer pageIdx, Integer itemsPerPage) {
        return bsUserInfoService.getConnectionRequests(userId, pageIdx, itemsPerPage);
    }

    @GetMapping("/{userId}/connections/blocked")
    public Mono<List<UserConnectionInfoResponse>> getBlockedConnections(@PathVariable String userId, Integer pageIdx, Integer itemsPerPage) {
        return bsUserInfoService.getBlockedConnections(userId, pageIdx, itemsPerPage);
    }

    @GetMapping("/{userId}/connections")
    public Mono<List<UserConnectionInfoResponse>> getConnectedUsers(@PathVariable String userId, Integer pageIdx, Integer itemsPerPage) {
        return bsUserInfoService.getConnectedUsers(userId, pageIdx, itemsPerPage);
    }

    @GetMapping
    public Mono<List<UserInfoResponse>> getUsers(Integer pageIdx, Integer itemsPerPage) {
        return bsUserInfoService.getUsers(pageIdx, itemsPerPage);
    }
    @PostMapping("/create")
    public Mono<String> create(@RequestBody UserInfoRequest userInfoRequest) {
        return bsUserInfoService.createConnection(userInfoRequest);
    }
    @PostMapping("/search")
    public Mono<List<UserInfoResponse>> search(@RequestBody Filter filter) {
        return bsUserInfoService.search(filter);
    }
    @GetMapping("/{id}/profile")
    public Mono<UserInfoResponse> getUserProfileById(@PathVariable String id) {
        return bsUserInfoService.findProfile(id);
    }
    @GetMapping("/{id}/non-connected-users")
    public Mono<List<UserConnectionInfoResponse>> getNonConnectedUsers(@PathVariable String id, Integer pageIdx, Integer itemsPerPage) {
        return bsUserInfoService.getNonConnectedUsers(id, pageIdx, itemsPerPage);
    }*/
}
