package com.mainul35.auth.services;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import com.mainul35.auth.dtos.UserLoginDto;

public interface UserService {

    void createUser(UserAuthInfoDto userInfoDto);

    UserAuthInfoDto retrieveUserInfo(String userId);

    UserAuthInfoDto loginUser(UserLoginDto userLoginDto);
}
