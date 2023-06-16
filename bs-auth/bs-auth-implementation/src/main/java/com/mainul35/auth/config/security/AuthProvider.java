package com.mainul35.auth.config.security;

import com.mainul35.auth.exceptions.ApplicationAuthenticationException;
import com.mainul35.auth.exceptions.InvalidTokenException;
import com.mainul35.auth.models.UserEntity;
import com.mainul35.auth.repositories.RoleRepository;
import com.mainul35.auth.repositories.UserRepository;
import com.mainul35.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        final String token = (String) usernamePasswordAuthenticationToken.getCredentials();
        if (token.isEmpty()) {
            return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }
        Optional<UserEntity> optionalUserModel = userRepository.findByUsername(username);

        if (optionalUserModel.isPresent()) {
            UserEntity markdownUserModel = optionalUserModel.get();
            try {
                tokenService.validateToken(markdownUserModel.getJwtToken());
            } catch (InvalidTokenException e) {
                markdownUserModel.setJwtToken(null);
                userRepository.save(markdownUserModel);
                return null;
            }

            return new User(username, "",
                    AuthorityUtils.createAuthorityList(markdownUserModel.getRoles().stream().map(s -> "ROLE_" + s).toArray(String[]::new))
            );
        }
        throw new ApplicationAuthenticationException("No such user found with this username");
    }
}
