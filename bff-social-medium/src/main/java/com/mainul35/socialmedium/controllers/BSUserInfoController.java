package com.mainul35.socialmedium.controllers;

import com.mainul35.socialmedium.services.BSUserInfoService;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin(origins = "${crossOrigin}")
@RequestMapping("/userinfo/users")
public class BSUserInfoController {

    private final BSUserInfoService bsUserInfoService;

    public BSUserInfoController(BSUserInfoService userInfoService) {
        this.bsUserInfoService = userInfoService;
    }

    @PostMapping("/{userId}/connections/request/{connectionId}")
    public Mono<?> requestConnection(String userId, String connectionId) {
        return bsUserInfoService.requestConnection(userId, connectionId);
    }


    @PutMapping("/{userId}/connections/accept/{connectionId}")
    public Mono<?> acceptConnection(String userId, String connectionId) {
        return null;
    }

    @PutMapping("/{userId}/connections/reject/{connectionId}")
    public Mono<?> rejectConnection(String userId, String connectionId) {
        return null;
    }

    @PutMapping("/{userId}/connections/block/{connectionId}")
    public Mono<?> blockConnection(String userId, String connectionId) {
        return null;
    }

    @PutMapping("/{userId}/connections/unblock/{connectionId}")
    public Mono<?> unblockConnection(String userId, String connectionId) {
        return null;
    }

    @GetMapping("/{userId}/connections/requests")
    public Mono<List<UserConnectionInfoResponse>> getConnectionRequests(String userId, Integer pageIxd, Integer itemsPerPage) {
        return null;
    }

    @GetMapping("/{userId}/connections/blocked")
    public Mono<List<UserConnectionInfoResponse>> getBlockedConnections(String userId, Integer pageIxd, Integer itemsPerPage) {
        return null;
    }

    @GetMapping("/{userId}/connections")
    public Mono<List<UserConnectionInfoResponse>> getConnectedUsers(String userId, Integer pageIxd, Integer itemsPerPage) {
        return null;
    }

    @GetMapping
    public Mono<List<UserInfoResponse>> getUsers(Integer pageIdx, Integer itemsPerPage) {
        return bsUserInfoService.getUsers(pageIdx, itemsPerPage);
    }
    @PostMapping("/create")
    public Mono<String> create(UserInfoRequest userInfoRequest) {
        return null;
    }
    @PostMapping("/search")
    public Mono<List<UserInfoResponse>> search(Filter Filter) {
        return null;
    }
    @GetMapping("/{id}/profile")
    public Mono<UserInfoResponse> getUserProfileById(String id) {
        return null;
    }
    @GetMapping("/{id}/non-connected-users")
    public Mono<List<UserConnectionInfoResponse>> getNonConnectedUsers(String id, Integer pageIxd, Integer itemsPerPage) {
        return null;
    }

}
