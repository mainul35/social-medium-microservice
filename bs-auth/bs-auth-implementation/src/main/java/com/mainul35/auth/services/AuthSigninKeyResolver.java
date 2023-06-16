package com.mainul35.auth.services;

import io.jsonwebtoken.SigningKeyResolver;

import javax.crypto.SecretKey;


public interface AuthSigninKeyResolver extends SigningKeyResolver {

    SecretKey getSecretKey();
}
