package com.mainul35.auth.services.impl;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import com.mainul35.auth.dtos.UserLoginDto;
import com.mainul35.auth.models.RoleEntity;
import com.mainul35.auth.models.UserEntity;
import com.mainul35.auth.repositories.RoleRepository;
import com.mainul35.auth.repositories.UserRepository;
import com.mainul35.auth.services.TokenService;
import com.mainul35.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserAuthInfoDto userInfoDto) {

        if (userRepository.findByUsername(userInfoDto.getUsername()).isPresent()) {
            throw new DuplicateKeyException("Username is already registered");
        }
        // Transform userInfoDto to markdown role model
        UserEntity userEntity = new UserEntity(userInfoDto);

        // Hash the password first
        checkNotNull(userInfoDto.getPassword());

        if (Optional.ofNullable(userInfoDto.getRoles()).isEmpty()) {
            userInfoDto.setRoles(Collections.singletonList("USER"));
        }

        userEntity.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        // Assign default role for user
        userEntity.setRoles(
                roleRepository.findAll().stream()
                        .filter(role -> matchRole(role.getRole(), userInfoDto.getRoles()))
                        .collect(Collectors.toList())
        );

        // Generate a new token for the user
        tokenService.generateToken(userEntity);

        // Save markdown role model
        userRepository.save(userEntity);

        // update the userInfoDto after the markdown role Model has been saved
        userInfoDto.mapEntityToDto(userEntity);
    }

    private boolean matchRole(String role, List<String> roles) {
        return roles.contains(role);
    }

    @Override
    public UserAuthInfoDto retrieveUserInfo(String userId) {
        Optional<UserEntity> markdownUserModel = userRepository.findById(userId);
        if (markdownUserModel.isPresent()) {
            UserAuthInfoDto userInfoDto = new UserAuthInfoDto();
            userInfoDto.mapEntityToDto(markdownUserModel.get());
            return userInfoDto;
        }
        return null;
    }

    @Override
    public UserAuthInfoDto loginUser(UserLoginDto userLoginDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(userLoginDto.getUsername());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
                tokenService.generateToken(userEntity);
                var dto = modelMapper.map(userEntity, UserAuthInfoDto.class);
                dto.setPassword("*****");
                return dto;
            } else {
                throw new BadCredentialsException("Password mismatched");
            }
        } else {
            throw new BadCredentialsException("Invalid username");
        }
    }
}
