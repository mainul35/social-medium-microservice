package com.mainul35.bsuserinfo.controllers;

import com.mainul35.bsuserinfo.entity.UserEntity;
import com.mainul35.bsuserinfo.services.definition.UserConnectionService;
import com.mainul35.bsuserinfo.services.definition.UserInfoService;
import controllers.definition.IUserInfoController;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserInfoController implements IUserInfoController {

    private final UserInfoService userInfoService;
    private final UserConnectionService userConnectionService;
    UserInfoController(UserInfoService userInfoService1, UserConnectionService userConnectionService1) {
        this.userInfoService = userInfoService1;

        this.userConnectionService = userConnectionService1;
    }
    @Override
    public ResponseEntity<List<UserInfoResponse>> getUsers(Integer pageIxd, Integer itemsPerPage) {
        var userEntityList = userInfoService.getUsers(pageIxd, itemsPerPage);
        return ResponseEntity.ok(convertUserEntityListToUserInfoResponseList(userEntityList));
    }

    private List<UserInfoResponse> convertUserEntityListToUserInfoResponseList(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(userEntity -> {
            UserInfoResponse response = new UserInfoResponse();
            BeanUtils.copyProperties(userEntity, response);
            return response;
        }).toList();
    }

    @Override
    public ResponseEntity<String> create(UserInfoRequest userInfoRequest) {
        userInfoService.create(userInfoRequest);
        return ResponseEntity.ok("Successfully created user");
    }

    @Override
    public ResponseEntity<List<UserInfoResponse>> search(Filter filter) {
        var userEntityList = userInfoService.searchUser(filter);
        return ResponseEntity.ok(convertUserEntityListToUserInfoResponseList(userEntityList));
    }

    @Override
    public ResponseEntity<UserInfoResponse> getUserProfileById(String id) {
        var user = this.userInfoService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<UserConnectionInfoResponse>> getNonConnectedUsers(String id, Integer pageIxd, Integer itemsPerPage) {
        return ResponseEntity.ok(this.userConnectionService.getNonConnectedUsers(id, pageIxd, itemsPerPage));
    }
}
