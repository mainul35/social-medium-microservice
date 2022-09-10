package com.mainul35.auth.services;


import com.mainul35.auth.dtos.RoleDto;

public interface RoleService {

    void createRole(RoleDto roleDto);

    RoleDto roleInfo(String roleId);
}
