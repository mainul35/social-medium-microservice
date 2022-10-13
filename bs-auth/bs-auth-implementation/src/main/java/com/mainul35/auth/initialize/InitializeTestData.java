package com.mainul35.auth.initialize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainul35.auth.models.RoleEntity;
import com.mainul35.auth.models.UserEntity;
import com.mainul35.auth.repositories.RoleRepository;
import com.mainul35.auth.repositories.UserRepository;
import com.mainul35.auth.services.TokenService;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Profile({"dev", "test"})
@Component
public class InitializeTestData implements InitializeData {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final ResourceLoader resourceLoader;

    public InitializeTestData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenService tokenService, ResourceLoader resourceLoader) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void initialize() {
        addRoles();
//        addUsers();
    }

    private void addRoles() {
        try {
            List<RoleEntity> markdownRoleModels = new ObjectMapper()
                    .readValue(
                            resourceLoader.getResource("classpath:roles.json").getInputStream(),
                            new TypeReference<ArrayList<RoleEntity>>() {
                            }
                    );
            markdownRoleModels.forEach(markdownRoleModel -> {
                if (roleRepository.findByRole(markdownRoleModel.getRole()).isEmpty()) {
                    roleRepository.saveAndFlush(markdownRoleModel);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUsers() {
        userRepository.deleteAll();
        try {
            List<UserEntity> userEntities = new ObjectMapper()
                    .readValue(
                            resourceLoader.getResource("classpath:users.json").getInputStream(),
                            new TypeReference<ArrayList<UserEntity>>() {
                            }
                    );
                    userEntities.forEach(userEntity -> {
                tokenService.generateToken(userEntity);
                userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                userRepository.saveAndFlush(userEntity);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
