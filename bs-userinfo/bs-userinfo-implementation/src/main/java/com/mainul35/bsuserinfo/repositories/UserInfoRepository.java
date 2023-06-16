package com.mainul35.bsuserinfo.repositories;

import com.mainul35.bsuserinfo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<UserEntity, String>, JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(String s);

    @Transactional
    @Query(value = "select u from UserEntity u where u.id <> :userId ")
    Stream<UserEntity> findAllExceptUser(String userId);
}
