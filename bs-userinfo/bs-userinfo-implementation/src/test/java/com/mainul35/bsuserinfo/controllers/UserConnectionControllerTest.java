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
        assertEquals(resp.getBody().getStatus(), ConnectionStatus.REQUESTED);
    }

    @Test
    @Order(2)
    void acceptConnection() {
        var resp = userConnectionController.acceptConnection(connectionId, userId);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertEquals(resp.getBody().getStatus(), ConnectionStatus.ACCEPTED);
    }

    @Test
    @Order(3)
    void rejectConnection() {

    }

    @Test
    @Order(4)
    void blockConnection() {
    }

    @Test
    @Order(5)
    void unblockConnection() {
    }

    @Test
    @Order(6)
    void getConnectionRequests() {
    }

    @Test
    @Order(7)
    void getBlockedConnections() {
    }

    @Test
    @Order(8)
    void getConnectedUsers() {
    }
}