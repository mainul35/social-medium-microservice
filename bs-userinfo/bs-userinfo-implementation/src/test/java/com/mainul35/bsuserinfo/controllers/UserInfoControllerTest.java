package com.mainul35.bsuserinfo.controllers;

import com.mainul35.bsuserinfo.config.exceptions.DuplicateEntryException;
import com.mainul35.bsuserinfo.initialize.InitializeData;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserInfoControllerTest {

    @Autowired
    private UserInfoController userInfoController;

    @Autowired
    private InitializeData initializeData;

    private UserInfoRequest userInfoRequest;

    @BeforeEach
    void setUp() {
        userInfoRequest = new UserInfoRequest();
        userInfoRequest.setEmail("mainuls18+0011@gmail.com");
        userInfoRequest.setUsername("mainul_11");
        userInfoRequest.setSurname("Hasan");
        userInfoRequest.setFirstName("Syed Mainul");
        userInfoRequest.setProfileImagePath("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745");

        initializeData.initialize();
    }

    @Test
    void getUsers() {
        var resp = userInfoController.getUsers(1, 5);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var list = resp.getBody();
        assertEquals(5, list.size());
    }

    @Test
    void create() {
        try {
            var resp = userInfoController.create(userInfoRequest);
            assertTrue(resp.getStatusCode().is2xxSuccessful());
        } catch (DuplicateEntryException e) {
            log.error(e.getMessage());
        }
    }

    @Test
    void search() {
        var filter = new Filter();
        filter.setField("email");
        filter.setValue("mainuls18+0011@gmail.com");
        var resp = userInfoController.search(filter);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var list = resp.getBody();
        assertEquals("mainul_11", list.get(0).getUsername());
    }

    @Test
    void getUserProfileById() {
        var resp = userInfoController.getUserProfileById(userInfoRequest.getUsername());
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var body = resp.getBody();
        assertEquals(userInfoRequest.getEmail(), body.getEmail());
    }

    @Test
    void getNonConnectedUsers() {
        var resp = userInfoController.getNonConnectedUsers(userInfoRequest.getUsername(), 1, 10);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        var list = resp.getBody();
        assertTrue(list.size() > 0);
    }
}