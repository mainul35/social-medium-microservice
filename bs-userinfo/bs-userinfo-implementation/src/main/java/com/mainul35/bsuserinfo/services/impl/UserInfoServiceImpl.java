package com.mainul35.bsuserinfo.services.impl;

import com.mainul35.bsuserinfo.config.exceptions.DuplicateEntryException;
import com.mainul35.bsuserinfo.config.rabbitmq.RabbitMQConfig;
import com.mainul35.bsuserinfo.entity.UserConnection;
import com.mainul35.bsuserinfo.entity.UserConnectionId;
import com.mainul35.bsuserinfo.entity.UserEntity;
import com.mainul35.bsuserinfo.exceptions.NoContentException;
import com.mainul35.bsuserinfo.repositories.UserConnectionRepository;
import com.mainul35.bsuserinfo.repositories.UserInfoRepository;
import com.mainul35.bsuserinfo.services.definition.UserInfoService;
import controllers.dtos.enums.ConnectionStatus;
import controllers.dtos.enums.Field;
import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserInfoResponse;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserConnectionRepository userConnectionRepository;
    private final AmqpTemplate rabbitTemplate;
    @PersistenceContext
    EntityManager em;
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserConnectionRepository userConnectionRepository, AmqpTemplate rabbitTemplate) {
        this.userInfoRepository = userInfoRepository;
        this.userConnectionRepository = userConnectionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<UserEntity> getUsers(Integer pageIxd, Integer itemsPerPage) {
        Pageable pagable = PageRequest.of(pageIxd - 1, itemsPerPage, Sort.by(Sort.Order.asc("username")));
        return userInfoRepository.findAll(pagable).toList();
    }
    @Override
    public void create(UserInfoRequest userInfoRequest) {
        userInfoRepository.findByUsername(userInfoRequest.getUsername()).ifPresent(user -> {
            throw new DuplicateEntryException("User already exists");
        });
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userInfoRequest, userEntity);
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setProfileImagePath("https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745");
        var savedUser = userInfoRepository.save(userEntity);
        log.debug("Sending user object to exchange...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.RMQ_NAME, savedUser.getId());
    }
    @Override
    @Transactional
    @RabbitListener(queues = RabbitMQConfig.RMQ_NAME)
    public synchronized void createUserConnections(String userId) {
        var savedUserOptional = userInfoRepository.findById(userId);
        log.debug("Receiving user object from Queue");
        savedUserOptional.ifPresent(savedUser -> userInfoRepository.findAllExceptUser(userId).forEach(user -> {
            log.info(String.format("%s:%s", user.getId(), savedUser.getId()));
            var userConnection = new UserConnection();
            userConnection.setUserConnectionId(new UserConnectionId(savedUser, user));
            userConnection.setConnectionStatus(ConnectionStatus.NEW);
            var fetchedUserConnection =
                    userConnectionRepository.findByUserConnectionId(userConnection.getUserConnectionId())
                            .or(() -> userConnectionRepository.findByUserConnectionId(new UserConnectionId(user, savedUser)));
            if (fetchedUserConnection.isEmpty()) {
                userConnectionRepository.saveAndFlush(userConnection);
            }
        }));
    }

    @Override
    public List<UserEntity> searchUser(Filter filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = cq.from(UserEntity.class);
        cq.select(root);
        if (filter.getField().equals(Field.username.getValue())) {
            cq.where(cb.equal(root.get(Field.username.getValue()), filter.getValue()));
        }
        else if (filter.getField().equals(Field.email.getValue())) {
            cq.where(cb.equal(root.get(Field.email.getValue()), filter.getValue()));
        } else {
            throw new RuntimeException("Search criteria doesn't match");
        }
        var query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public UserInfoResponse getUserById(String id) {
        var user = userInfoRepository.findByUsername(id)
                .orElseThrow(() -> new NoContentException("No user found with this id"));
        var userResp = new UserInfoResponse();
        BeanUtils.copyProperties(user, userResp);
        return userResp;
    }
}
