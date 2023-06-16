package com.mainul35.auth.controllers;

import com.mainul35.auth.exceptions.InvalidTokenException;
import com.mainul35.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/auth/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(HttpServletRequest request) throws Exception {
        String jwtToken = "";
        String authHeader = request.getHeader(AUTHORIZATION);
        if (!authHeader.isEmpty()) {
            jwtToken = authHeader.split("\\s")[1];
        }

        var isValid = tokenService.validateToken(jwtToken);
        if (!isValid) {
            throw new InvalidTokenException("Invalid JWT Token");
        }
        return ResponseEntity.ok(isValid);
    }
}
