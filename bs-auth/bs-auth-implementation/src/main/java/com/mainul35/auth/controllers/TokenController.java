package com.mainul35.auth.controllers;

import com.mainul35.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/auth/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/validate")
    public void validateToken(HttpServletRequest request) throws Exception {
        String jwtToken = "";
        String authHeader = request.getHeader(AUTHORIZATION);
        if (!authHeader.isEmpty()) {
            jwtToken = authHeader.split("\\s")[1];
        }
        tokenService.validateToken(jwtToken);
    }
}
