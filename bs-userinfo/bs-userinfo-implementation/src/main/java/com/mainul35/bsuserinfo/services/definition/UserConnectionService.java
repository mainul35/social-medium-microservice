package com.mainul35.bsuserinfo.services.definition;

import com.mainul35.bsuserinfo.entity.UserConnection;
import controllers.dtos.response.UserConnectionInfoResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface UserConnectionService {
    @Transactional
    UserConnection addConnection(String userId, String connectionId);

    UserConnectionInfoResponse acceptConnection(String userId, String connectionId);

    UserConnectionInfoResponse rejectConnection(String userId, String connectionId);

    UserConnectionInfoResponse blockConnection(String userId, String connectionId);

    UserConnectionInfoResponse unblockConnection(String userId, String connectionId);

    Stream<UserConnectionInfoResponse> getAllConnectionRequests(String userId, Integer pageIxd, Integer itemsPerPage);

    Stream<UserConnectionInfoResponse> getAllBlockedConnections(String userId, Integer pageIxd, Integer itemsPerPage);

    Stream<UserConnectionInfoResponse> getAllAcceptedConnections(String userId, Integer pageIxd, Integer itemsPerPage);

    @Transactional
    Stream<UserConnectionInfoResponse> getNonConnectedUsers(String id, Integer pageIxd, Integer itemsPerPage);
}
