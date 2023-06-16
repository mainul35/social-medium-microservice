package com.mainul35.auth.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainul35.auth.exceptions.InvalidPayloadException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthFIlter extends AbstractAuthenticationProcessingFilter {

    public AuthFIlter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public AuthFIlter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String tokenUnstripped = httpServletRequest.getHeader(AUTHORIZATION);
        String token = StringUtils.removeStart(Optional.ofNullable(tokenUnstripped).orElse(""), "Bearer").trim();

        Authentication authentication;
        if (token.isEmpty()) {
            authentication = new UsernamePasswordAuthenticationToken("guest", "");
        } else {
            String[] splitToken = token.split("\\.");

            // splitToken[0] is the header, splitToken[1] is the payload and
            // splitToken[2] is the signature
            byte[] decodedBytes = Base64.getDecoder().decode(splitToken[1]);

            // You don't have to convert it to string but it really depends on what type
            // data you expect
            String payload = new String(decodedBytes, StandardCharsets.UTF_8);
            Map<String, String> map = this.mapPayload(payload);
            if (map == null || map.get("sub") == null) throw new InvalidPayloadException("Invalid payload");
            String username = map.get("sub");
            authentication = new UsernamePasswordAuthenticationToken(username, token);
        }
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", failed.getCause());
        jsonObject.put("errorMessage", failed.getMessage());

        response.getWriter().print(jsonObject);
        response.getWriter().flush();
    }

    private Map mapPayload(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        String json = payload;

        try {

            // convert JSON string to Map
            Map<String, String> map = mapper.readValue(json, Map.class);

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
