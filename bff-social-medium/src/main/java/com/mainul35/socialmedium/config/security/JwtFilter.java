package com.mainul35.socialmedium.config.security;

import com.mainul35.socialmedium.config.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.mainul35.socialmedium.handlers.BSAuthHandler;
import com.mainul35.socialmedium.services.BSAuthService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class JwtFilter implements WebFilter {

    private final BSAuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var headers = exchange.getRequest().getHeaders();
        if (headers.containsKey("authorization")) {
            var authorizationHeaders = exchange.getRequest().getHeaders().get("authorization");
            var headerOptional = authorizationHeaders.stream().filter(s -> s.contains("bearer")).findFirst();

            return headerOptional
                    .map(s -> authService.validateToken(s)
                            .filter(boolVal -> boolVal == true)
                            .flatMap(arg0 -> chain.filter(exchange)))
                    .orElseThrow(() -> new UnauthorizedException("UNAUTHORIZED"));
        }
        throw new UnauthorizedException("UNAUTHORIZED");
    }
}
