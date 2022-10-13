package com.mainul35.socialmedium.config.security;

import com.mainul35.socialmedium.config.exceptions.UnauthorizedException;
import com.mainul35.socialmedium.services.BSAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class JwtFilter implements WebFilter {
    @Autowired
    private BSAuthService authService;

    private String[] publicPaths;

    public JwtFilter(String[] publicPaths) {
        this.publicPaths = publicPaths;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var publicPathOptional = Arrays.stream(publicPaths).filter(s -> s.equals(exchange.getRequest().getPath().value()))
                .findFirst();
        if (publicPathOptional.isPresent()) {
            return chain.filter(exchange);
        }

        var headers = exchange.getRequest().getHeaders();
        if (headers.containsKey("authorization")) {
            var authorizationHeaders = exchange.getRequest()
                    .getHeaders()
                    .get("authorization");
            var headerOptional = authorizationHeaders
                    .stream()
                    .filter(s -> s.contains("bearer"))
                    .findFirst();

            if (headerOptional.isPresent()) {
                var authHeader = headerOptional.get();
                return authService.validateToken(authHeader)
                        .filter(aBoolean -> aBoolean == true)
                        .flatMap(aBoolean -> chain.filter(exchange));
            }
        }
        throw new UnauthorizedException("No JWT token was provided");
    }
}
