package com.mainul35.bsuserinfo.initialize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainul35.bsuserinfo.repositories.UserInfoRepository;
import com.mainul35.bsuserinfo.services.impl.UserInfoServiceImpl;
import controllers.dtos.request.UserInfoRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile({"dev"})
@Component
public class InitializeDevData implements InitializeData {

    private final UserInfoRepository userRepository;

    private final UserInfoServiceImpl userInfoService;

    private final ResourceLoader resourceLoader;

    public InitializeDevData(UserInfoRepository userRepository, UserInfoServiceImpl userInfoService, ResourceLoader resourceLoader) {
        this.userRepository = userRepository;
        this.userInfoService = userInfoService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void initialize() {
        addUsers ();
    }

    @Override
    public void doCleanUp() {
//        userRepository.deleteAll ();
    }

    private void addUsers() {

        InitializeTestData.populateUsers(userRepository, resourceLoader, userInfoService);
    }
}
