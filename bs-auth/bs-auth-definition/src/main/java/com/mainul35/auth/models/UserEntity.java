package com.mainul35.auth.models;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "auth_users")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends GenericModel {

    @Column(unique = true)
    private String username;

    @Column(columnDefinition="TEXT")
    private String jwtToken;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<RoleEntity> roles;

    public UserEntity() {
        super();
    }

    public UserEntity(UserAuthInfoDto userInfoDto) {
        this.setId(userInfoDto.getId() == null ? UUID.randomUUID().toString() : userInfoDto.getId());
        this.setUsername(userInfoDto.getUsername());
    }
}
