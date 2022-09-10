package com.mainul35.auth.dtos;

import com.mainul35.auth.models.UserEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserAuthInfoDto extends BaseDto<UserEntity> {
    private String id;
    private String username;
    private List<String> roles;
    private String password;
    private String jwtToken;
    private Date createdAt;
    private Date modifiedAt;

    public UserAuthInfoDto() {
    }

    public void mapEntityToDto(UserEntity userEntity) {
        this.setId(userEntity.getId());
        this.setUsername(userEntity.getUsername());
        this.setRoles(userEntity.getRoles());
        this.setPassword("******");
        this.setJwtToken(userEntity.getJwtToken());
        this.setCreatedAt(userEntity.getCreatedAt());
        this.setModifiedAt(userEntity.getUpdatedAt());
    }
}
