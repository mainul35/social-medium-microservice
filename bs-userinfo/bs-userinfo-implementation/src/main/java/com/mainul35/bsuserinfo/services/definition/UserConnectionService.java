package com.mainul35.bsuserinfo.services.definition;

import com.mainul35.bsuserinfo.entity.UserConnection;
import controllers.dtos.response.UserConnectionInfoResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

public interface UserConnectionService {
    @Transactional
    UserConnection addConnection(String username, String connectionUsername);

    UserConnectionInfoResponse acceptConnection(String username, String connectionUsername);

    UserConnectionInfoResponse rejectConnection(String username, String connectionUsername);

    UserConnectionInfoResponse blockConnection(String username, String connectionUsername);

    UserConnectionInfoResponse unblockConnection(String username, String connectionUsername);

    @Transactional
    List<UserConnectionInfoResponse> getAllConnectionRequests(String username, Integer pageIxd, Integer itemsPerPage);

    @Transactional
    List<UserConnectionInfoResponse> getAllBlockedConnections(String username, Integer pageIxd, Integer itemsPerPage);

    @Transactional
    List<UserConnectionInfoResponse> getAllAcceptedConnections(String username, Integer pageIxd, Integer itemsPerPage);

    @Transactional
    List<UserConnectionInfoResponse> getNonConnectedUsers(String username, Integer pageIxd, Integer itemsPerPage);
}
