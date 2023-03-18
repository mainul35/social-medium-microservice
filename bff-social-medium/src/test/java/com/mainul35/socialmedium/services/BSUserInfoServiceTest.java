package com.mainul35.socialmedium.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.dtos.enums.ConnectionStatus;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BSUserInfoServiceTest {

    public static MockWebServer mockBackEnd;

    private BSUserInfoService bsUserInfoService;

    private ObjectMapper objectMapper;

    private UserConnectionInfoResponse response;

    private final String USER_ID;
    private final String CONNECTION_ID;

    public BSUserInfoServiceTest() {
        USER_ID = UUID.randomUUID().toString();
        CONNECTION_ID = UUID.randomUUID().toString();;
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        bsUserInfoService = new BSUserInfoService(baseUrl);

        objectMapper = new ObjectMapper();

        initTestData();
    }

    private void initTestData() {
        response = new UserConnectionInfoResponse();
        UserInfoResponse user = new UserInfoResponse();
        user.setEmail("mainuls18+001@gmail.com");
        user.setUsername("mainul_1");
        user.setId(USER_ID);
        user.setFirstName("Syed");
        user.setSurname("Hasan");

        UserInfoResponse connection = new UserInfoResponse();
        connection.setEmail("mainuls18+002@gmail.com");
        connection.setUsername("mainul_2");
        connection.setId(CONNECTION_ID);
        connection.setFirstName("Syed");
        connection.setSurname("Hasan");

        response.setUser(user);
        response.setConnection(connection);
        response.setStatus(ConnectionStatus.NEW);
    }

    @Test
    public void test_NewConnectionRequest() throws JsonProcessingException {

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        var userConnectionInfoResponseMono = bsUserInfoService.requestConnection("1", "2");

        StepVerifier.create(userConnectionInfoResponseMono)
                .expectNextMatches(userConnectionInfoResponse ->
                        userConnectionInfoResponse.getUser().getEmail().equals("mainuls18+001@gmail.com")
                        && userConnectionInfoResponse.getConnection().getEmail().equals("mainuls18+002@gmail.com")
                        && userConnectionInfoResponse.getStatus().equals(ConnectionStatus.NEW))
                .verifyComplete();
    }

    @Test
    void test_getUsers() throws JsonProcessingException {
        List<UserInfoResponse> users = new ArrayList<>();
        users.add(response.getUser());
        users.add(response.getConnection());
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(objectMapper. writeValueAsString(users))
                    .addHeader("Content-Type", "application/json"));

            var userInfoResponsesMono = bsUserInfoService.getUsers(0, 2);

            StepVerifier.create(userInfoResponsesMono)
                    .expectNextMatches(userInfoResponses -> userInfoResponses.size() == 2)
                    .verifyComplete();
    }

    @Test
    void test_acceptConnection() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.ACCEPTED);
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(objectMapper.writeValueAsString(response))
                    .addHeader("Content-Type", "application/json"));

            var userConnectionInfoResponseMono = bsUserInfoService.acceptConnection("1", "2");

            StepVerifier.create(userConnectionInfoResponseMono)
                    .expectNextMatches(userConnectionInfoResponse ->
                            userConnectionInfoResponse.getUser().getUsername().equals("mainul_1")
                                    && userConnectionInfoResponse.getConnection().getUsername().equals("mainul_2")
                                    && userConnectionInfoResponse.getStatus().equals(ConnectionStatus.ACCEPTED))
                    .verifyComplete();
    }

    @Test
    void test_rejectConnection() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.REJECTED);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        var userConnectionInfoResponseMono = bsUserInfoService.rejectConnection("1", "2");

        StepVerifier.create(userConnectionInfoResponseMono)
                .expectNextMatches(userConnectionInfoResponse ->
                        userConnectionInfoResponse.getUser().getUsername().equals("mainul_1")
                                && userConnectionInfoResponse.getConnection().getUsername().equals("mainul_2")
                                && userConnectionInfoResponse.getStatus().equals(ConnectionStatus.REJECTED))
                .verifyComplete();
    }

    @Test
    void test_blockConnection() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.BLOCKED);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        var userConnectionInfoResponseMono = bsUserInfoService.blockConnection("1", "2");

        StepVerifier.create(userConnectionInfoResponseMono)
                .expectNextMatches(userConnectionInfoResponse ->
                        userConnectionInfoResponse.getUser().getUsername().equals("mainul_1")
                                && userConnectionInfoResponse.getConnection().getUsername().equals("mainul_2")
                                && userConnectionInfoResponse.getStatus().equals(ConnectionStatus.BLOCKED))
                .verifyComplete();
    }

    @Test
    void test_unblockConnection() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.UNBLOCKED);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        var userConnectionInfoResponseMono = bsUserInfoService.unblockConnection("1", "2");

        StepVerifier.create(userConnectionInfoResponseMono)
                .expectNextMatches(userConnectionInfoResponse ->
                        userConnectionInfoResponse.getUser().getUsername().equals("mainul_1")
                                && userConnectionInfoResponse.getConnection().getUsername().equals("mainul_2")
                                && userConnectionInfoResponse.getStatus().equals(ConnectionStatus.UNBLOCKED))
                .verifyComplete();
    }

    @Test
    void test_getConnectionRequests() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.REQUESTED);

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(List.of(response)))
                .addHeader("Content-Type", "application/json"));

        var connectionRequests = bsUserInfoService.getConnectionRequests("1", 1, 5);
        StepVerifier.create(connectionRequests)
                .expectNextMatches(userConnectionInfoResponses -> userConnectionInfoResponses.size() == 1
                        && userConnectionInfoResponses.get(0).getStatus().equals(ConnectionStatus.REQUESTED))
                .verifyComplete();
    }

    @Test
    void test_getBlockedConnections() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.BLOCKED);

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(List.of(response)))
                .addHeader("Content-Type", "application/json"));

        var connectionRequests = bsUserInfoService.getBlockedConnections("1", 1, 5);
        StepVerifier.create(connectionRequests)
                .expectNextMatches(userConnectionInfoResponses -> userConnectionInfoResponses.size() == 1
                && userConnectionInfoResponses.get(0).getStatus().equals(ConnectionStatus.BLOCKED))
                .verifyComplete();
    }

    @Test
    void test_getConnectedUsers() throws JsonProcessingException {
        response.setStatus(ConnectionStatus.ACCEPTED);

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(List.of(response)))
                .addHeader("Content-Type", "application/json"));

        var connectionRequests = bsUserInfoService.getConnectedUsers("1", 1, 5);
        StepVerifier.create(connectionRequests)
                .expectNextMatches(userConnectionInfoResponses -> userConnectionInfoResponses.size() == 1
                && userConnectionInfoResponses.get(0).getStatus().equals(ConnectionStatus.ACCEPTED))
                .verifyComplete();
    }

    @Test
    void test_createConnection() throws JsonProcessingException {
        UserInfoRequest request = new UserInfoRequest();
        request.setId(UUID.randomUUID().toString());
        request.setEmail("mainuls18+003@gmail.com");
        request.setSurname("Hasan");
        request.setFirstName("Syed Mainul");
        request.setUsername("mainul_3");

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(request.getUsername()))
                .addHeader("Content-Type", "application/json"));

        var resp = bsUserInfoService.createConnection(request).block();
        Assertions.assertEquals("mainul_3", resp);
    }

    @Test
    void test_search() {
    }

    @Test
    void test_findProfile() {
    }

    @Test
    void test_getNonConnectedUsers() {
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
}
