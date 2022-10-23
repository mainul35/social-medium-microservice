package com.mainul35.bsuserinfo.initialize;

import com.mainul35.bsuserinfo.repositories.UserInfoRepository;
import com.mainul35.bsuserinfo.services.impl.UserInfoServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Profile({"prod"})
@Component
public class InitializeProdData implements InitializeData {

    private final UserInfoRepository userRepository;

    private final UserInfoServiceImpl userInfoService;

    private final ResourceLoader resourceLoader;

    public InitializeProdData(UserInfoRepository userRepository, UserInfoServiceImpl userInfoService, ResourceLoader resourceLoader) {
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
        userRepository.deleteAll ();
    }

    private void addUsers() {

        InitializeTestData.populateUsers(userRepository, resourceLoader, userInfoService);
    }
}
