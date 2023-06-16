package com.mainul35.auth.models;

import com.mainul35.auth.dtos.UserAuthInfoDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "auth_users")
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
    @ToString.Exclude
    private List<RoleEntity> roles;

    public UserEntity(UserAuthInfoDto userInfoDto) {
        this.setId(userInfoDto.getId() == null ? UUID.randomUUID().toString() : userInfoDto.getId());
        this.setUsername(userInfoDto.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
