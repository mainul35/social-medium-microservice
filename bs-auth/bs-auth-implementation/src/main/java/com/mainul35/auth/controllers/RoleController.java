package com.mainul35.auth.controllers;

import com.google.common.base.Preconditions;
import com.mainul35.auth.dtos.RoleDto;
import com.mainul35.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/role")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        Preconditions.checkNotNull(roleDto);
        roleService.createRole(roleDto);
        return roleDto;
    }

    @GetMapping("/info/{roleId}")
    public RoleDto getRoleInfo(@PathVariable String roleId) {
        return roleService.roleInfo(roleId);
    }

    // TODO: delete role

    // TODO: update role
}
