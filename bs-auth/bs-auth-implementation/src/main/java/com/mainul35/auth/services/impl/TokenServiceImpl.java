package com.mainul35.auth.services.impl;

import com.mainul35.auth.exceptions.InvalidTokenException;
import com.mainul35.auth.models.UserEntity;
import com.mainul35.auth.repositories.UserRepository;
import com.mainul35.auth.services.AuthSigninKeyResolver;
import com.mainul35.auth.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private AuthSigninKeyResolver authSigninKeyResolver;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean validateToken(String jwtToken) throws InvalidTokenException {
        try {
            // Check token not null
            if (jwtToken == null)
                return false;

            // Parse token
            var jwtTokenObj = Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigninKeyResolver)
                    .build().parse(jwtToken);
            var map = (Map<String, Object>) jwtTokenObj.getBody();
            var exp = (Long) map.get("exp");

            // Check token not expired
            return System.currentTimeMillis() <= exp;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    @Override
    public void generateToken(UserEntity userEntity) {
        var claims = new HashMap<String, Object>();
        claims.put("iss", userEntity.getId());
        claims.put("exp", System.currentTimeMillis() + (1000 * 60 * 60 * 12));
        claims.put("iat", System.currentTimeMillis());
        claims.put("sub", userEntity.getUsername());
        claims.put("aud", userEntity.getRoles().toString());

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .signWith(authSigninKeyResolver.getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        if (validateToken(userEntity.getJwtToken())) {
            userEntity.setJwtToken(jwtToken.toString());
        } else {
            userEntity.setJwtToken(jwtToken.toString());
            userRepository.save(userEntity);
        }
    }
}
