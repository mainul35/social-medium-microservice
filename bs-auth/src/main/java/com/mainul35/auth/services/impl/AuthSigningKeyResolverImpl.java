package com.mainul35.auth.services.impl;

import com.mainul35.auth.services.AuthSigninKeyResolver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component
public class AuthSigningKeyResolverImpl implements AuthSigninKeyResolver {

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    private SecretKey secretKey;

    @Override
    public SecretKey getSecretKey() {
        if (isNull(secretKey)) {
            this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.secretKeyString.getBytes()));
        }
        return this.secretKey;
    }

    /**
     * Returns the signing key that should be used to validate a digital signature for the Claims JWS with the specified
     * header and claims.
     *
     * @param header the header of the JWS to validate
     * @param claims the claims (body) of the JWS to validate
     * @return the signing key that should be used to validate a digital signature for the Claims JWS with the specified
     * header and claims.
     */
    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        return this.getSecretKey();
    }

    /**
     * Returns the signing key that should be used to validate a digital signature for the Plaintext JWS with the
     * specified header and plaintext payload.
     *
     * @param header    the header of the JWS to validate
     * @param plaintext the plaintext body of the JWS to validate
     * @return the signing key that should be used to validate a digital signature for the Plaintext JWS with the
     * specified header and plaintext payload.
     */
    @Override
    public Key resolveSigningKey(JwsHeader header, String plaintext) {
        return this.getSecretKey();
    }
}
