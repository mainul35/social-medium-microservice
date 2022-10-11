package com.mainul35.socialmedium.dto.request;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import controllers.dtos.request.UserInfoRequest;


public record CreateUserRequest (UserInfoRequest userInfo, UserAuthInfoDto authInfo){}
