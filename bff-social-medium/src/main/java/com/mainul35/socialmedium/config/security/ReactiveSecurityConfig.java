package com.mainul35.socialmedium.config.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@AllArgsConstructor
public class ReactiveSecurityConfig {

    private final JwtFilter jwtFilter;
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
        .csrf().disable()
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
        .authorizeExchange()
        .pathMatchers("/users/create").permitAll()
        .pathMatchers("/auth/login").permitAll()
        .anyExchange()
        .permitAll();
//        .authenticated();
            
        return http.build();
    }
}
