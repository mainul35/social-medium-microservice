package com.mainul35.auth.services;

import com.mainul35.auth.exceptions.InvalidTokenException;
import com.mainul35.auth.models.UserEntity;

public interface TokenService {

    boolean validateToken(String jwtToken) throws InvalidTokenException;

    void generateToken(UserEntity userEntity);
}
