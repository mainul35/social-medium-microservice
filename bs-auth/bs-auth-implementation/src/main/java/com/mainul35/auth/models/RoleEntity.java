package com.mainul35.auth.models;

import com.mainul35.auth.dtos.RoleDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Data
@Entity(name = "roles")
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends GenericModel {

    @Column
    private String role;

    public RoleEntity() {
        super();
    }

    public RoleEntity(RoleDto roleDto) {
        this.setId(roleDto.getId() == null ? UUID.randomUUID().toString() : roleDto.getId());
        this.setRole(roleDto.getRole());
    }
}
