package com.mainul35.bsuserinfo.services.definition;

import com.mainul35.bsuserinfo.config.rabbitmq.RabbitMQConfig;
import com.mainul35.bsuserinfo.entity.UserEntity;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface UserInfoService {
    Stream<UserEntity> getUsers(Integer pageIxd, Integer itemsPerPage);

    void create(UserInfoRequest userInfoRequest);

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.RMQ_NAME)
    void createUserConnections(String userId);

    Stream<UserEntity> searchUser(Filter filter);

    UserInfoResponse getUserById(String id);
}
