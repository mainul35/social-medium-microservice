package com.mainul35.auth.services.impl;

import com.mainul35.auth.dtos.RoleDto;
import com.mainul35.auth.models.RoleEntity;
import com.mainul35.auth.repositories.RoleRepository;
import com.mainul35.auth.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createRole(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity(roleDto);
        roleRepository.save(roleEntity);
        roleDto.mapEntityToDto(roleEntity);
    }

    @Override
    public RoleDto roleInfo(String roleId) {
        Optional<RoleEntity> optionalMarkdownRoleModel = roleRepository.findById(roleId);
        if (optionalMarkdownRoleModel.isPresent()) {
            final RoleEntity markdownRoleModel = optionalMarkdownRoleModel.get();
            return modelMapper.map(markdownRoleModel, RoleDto.class);
        }
        return null;
    }
}
