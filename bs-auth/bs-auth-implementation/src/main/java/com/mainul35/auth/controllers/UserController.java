package com.mainul35.auth.controllers;

import com.google.common.base.Preconditions;
import com.mainul35.auth.dtos.UserAuthInfoDto;
import com.mainul35.auth.dtos.UserLoginDto;
import com.mainul35.auth.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ANONYMOUS', 'ADMIN')")
    public UserAuthInfoDto createUser(@RequestBody UserAuthInfoDto userInfoDto) {
        Preconditions.checkNotNull(userInfoDto);
        userService.createUser(userInfoDto);
        return userInfoDto;
    }

    @PostMapping("/login")
    public UserAuthInfoDto login(@RequestBody UserLoginDto userLoginDto) {
        Preconditions.checkNotNull(userLoginDto);
        return userService.loginUser(userLoginDto);
    }
    // TODO: Update user

    // TODO: Delete user
}
