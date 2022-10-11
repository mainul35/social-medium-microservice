package com.mainul35.socialmedium.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class ReactiveSecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers("/users/create").permitAll()
        .anyExchange()
        // .permitAll();
        .authenticated()
        .and()
        .httpBasic(); // Pure basic is not enough for us!
            
        return http.build();
    }
}
