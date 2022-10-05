package com.mainul35.auth.dtos;

import com.mainul35.auth.models.RoleEntity;
import lombok.Data;

@Data
public class RoleDto extends BaseDto<RoleEntity> {
    private String id;
    private String role;
    private String createdAt;
    private String modifiedAt;

    @Override
    public void mapEntityToDto(RoleEntity markdownRoleModel) {
        this.setId(markdownRoleModel.getId());
        this.setRole(markdownRoleModel.getRole());
        this.setCreatedAt(markdownRoleModel.getCreatedAt().toString());
        this.setModifiedAt(markdownRoleModel.getUpdatedAt().toString());
    }
}
