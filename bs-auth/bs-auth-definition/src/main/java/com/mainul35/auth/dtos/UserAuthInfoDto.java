package com.mainul35.auth.dtos;

import com.mainul35.auth.models.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthInfoDto extends BaseDto<UserEntity> {
    private String id;
    private String username;
    private List<String> roles;
    private String password;
    private String jwtToken;
    private Date createdAt;
    private Date modifiedAt;

    public void mapEntityToDto(UserEntity userEntity) {
        this.setId(userEntity.getId());
        this.setUsername(userEntity.getUsername());
        var roles = new ArrayList<String>();
        userEntity.getRoles().forEach(roleEntity -> {
            roles.add(roleEntity.getRole());
        });
        this.setRoles(roles);
        this.setPassword("******");
        this.setJwtToken(userEntity.getJwtToken());
        this.setCreatedAt(userEntity.getCreatedAt());
        this.setModifiedAt(userEntity.getUpdatedAt());
    }
}
