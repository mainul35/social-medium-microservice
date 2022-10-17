package com.mainul35.bsuserinfo.controllers;

import com.mainul35.bsuserinfo.initialize.InitializeData;
import controllers.dtos.enums.ConnectionStatus;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserConnectionControllerTest {

    @Autowired
    private UserConnectionController userConnectionController;

    @Autowired
    private InitializeData initializeData;

    private String userId;
    private String connectionId;

    @BeforeEach
    void setUp() {
        this.userId = "mainul_1";
        this.connectionId = "mainul_2";
        initializeData.initialize();
    }

    @Test
    @Order(1)
    void requestConnection() {
        var resp = userConnectionController.requestConnection(userId, connectionId);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var body = resp.getBody();
        assertEquals(body.getStatus(), ConnectionStatus.REQUESTED);
        assertEquals(body.getRequestedById(), body.getUser().getUsername());
    }

    @Test
    @Order(2)
    void getConnectionRequests() {
        // Send a request from mainul_1 to mainul_4
        var user4 = "mainul_4";
        var requestResp = userConnectionController.requestConnection(userId, user4);
        assertTrue(requestResp.getStatusCode().is2xxSuccessful());
        assertEquals(requestResp.getBody().getStatus(), ConnectionStatus.REQUESTED);

        // Get request list of mainul_4
        var totalConnectionReqResp = userConnectionController.getConnectionRequests(user4, 1, 5);
        var body = totalConnectionReqResp.getBody();
        assertTrue(totalConnectionReqResp.getStatusCode().is2xxSuccessful());
        assertEquals(1, body.toList().size());
    }

    @Test
    @Order(3)
    void acceptConnection() {
        var resp = userConnectionController.acceptConnection(connectionId, userId);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var body = resp.getBody();
        assertEquals(body.getStatus(), ConnectionStatus.ACCEPTED);
    }

    @Test
    @Order(4)
    void getConnectedUsers() {
        var resp = userConnectionController.getConnectedUsers(userId, 1, 5);
        var body = resp.getBody();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var list = body.toList();
        assertEquals(1, list.size());
        assertEquals(list.get(0).getStatus(), ConnectionStatus.ACCEPTED);
    }

    @Test
    @Order(5)
    void rejectConnection() {
        // Send a request from mainul_1 to mainul_3
        var user3 = "mainul_3";
        var requestResp = userConnectionController.requestConnection(userId, user3);
        assertTrue(requestResp.getStatusCode().is2xxSuccessful());
        assertEquals(requestResp.getBody().getStatus(), ConnectionStatus.REQUESTED);

        // Reject request
        var resp = userConnectionController.rejectConnection(user3, userId);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var body = resp.getBody();
        assertEquals(body.getStatus(), ConnectionStatus.REJECTED);
    }

    @Test
    @Order(6)
    void blockConnection() {
        // Send a request from mainul_1 to mainul_3
        var user3 = "mainul_3";
        var requestResp = userConnectionController.requestConnection(userId, user3);
        assertTrue(requestResp.getStatusCode().is2xxSuccessful());
        assertEquals(requestResp.getBody().getStatus(), ConnectionStatus.REQUESTED);

        // Block request
        var resp = userConnectionController.blockConnection(user3, userId);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var body = resp.getBody();
        assertEquals(body.getStatus(), ConnectionStatus.BLOCKED);
        assertEquals(body.getBlockedById(), body.getUser().getUsername());
    }

    @Test
    @Order(7)
    void getBlockedConnections() {
        var user3 = "mainul_3";
        var resp = userConnectionController.getBlockedConnections(user3, 1, 5);
        var body = resp.getBody();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertEquals(1, body.toList().size());
    }

    @Test
    @Order(8)
    void unblockConnection() {
        var user3 = "mainul_3";
        // Unblock request
        var resp = userConnectionController.unblockConnection(user3, userId);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var body = resp.getBody();
        assertEquals(body.getStatus(), ConnectionStatus.UNBLOCKED);
    }
}