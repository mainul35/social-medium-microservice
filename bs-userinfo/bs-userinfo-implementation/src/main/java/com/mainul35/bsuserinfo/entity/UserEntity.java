package com.mainul35.bsuserinfo.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "tbl_user")
@EqualsAndHashCode
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "profile_image_path")
    private String profileImagePath;
}
