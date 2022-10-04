package com.mainul35.socialmedium.controllers;

import com.mainul35.socialmedium.handlers.BSAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BSAuthController {

    @Autowired
    private BSAuthHandler bsAuthHandler;
    @Bean
    RouterFunction<ServerResponse> bsAuthFunction() {
        return RouterFunctions.route()
                .POST("/auth/login", RequestPredicates.contentType(MediaType.APPLICATION_JSON), bsAuthHandler::loginHandler)
                .build();
    }
}
