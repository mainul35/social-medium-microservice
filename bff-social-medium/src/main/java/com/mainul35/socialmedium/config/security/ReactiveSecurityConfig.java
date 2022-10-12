package com.mainul35.socialmedium.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@AllArgsConstructor
public class ReactiveSecurityConfig {
    private static String[] PUBLIC_PATHS = new String[] {
            "/users/create",
            "/auth/login"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers(PUBLIC_PATHS).permitAll()
        .anyExchange()
        .permitAll()
        .and().addFilterBefore(jwtFilter(), SecurityWebFiltersOrder.AUTHORIZATION);
            
        return http.build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(PUBLIC_PATHS);
    }
}
