package com.mainul35.bsuserinfo.repositories;

import controllers.dtos.enums.ConnectionStatus;
import com.mainul35.bsuserinfo.entity.UserConnection;
import com.mainul35.bsuserinfo.entity.UserConnectionId;
import com.mainul35.bsuserinfo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserConnectionRepository extends PagingAndSortingRepository<UserConnection, UserConnectionId>, JpaRepository<UserConnection, UserConnectionId> {
    Optional<UserConnection> findByUserConnectionId(UserConnectionId userConnectionId);
    Stream<UserConnection> findByUserConnectionId_User(UserEntity user);
    Stream<UserConnection> findByUserConnectionId_Connection(UserEntity user);
    Stream<UserConnection> findAllByUserConnectionId_UserAndConnectionStatus(UserEntity user, ConnectionStatus status);
    Stream<UserConnection> findAllByUserConnectionId_ConnectionAndConnectionStatus(UserEntity user, ConnectionStatus status);
}
