package com.mainul35.auth.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     * <p>
     * Any endpoint that requires defense against common vulnerabilities can be specified here, including public ones.
     * See {@link HttpSecurity#authorizeRequests} and the `permitAll()` authorization rule
     * for more details on public endpoints.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        var authManager = authenticationManagerBuilder.authenticationProvider(authenticationProvider).getOrBuild();
        return http
                .sessionManagement (session ->
                        session.sessionCreationPolicy (SessionCreationPolicy.STATELESS) // We don't need to create any session
                )
                .authenticationProvider (authenticationProvider)
                .addFilterBefore (authFIlter(authManager), AnonymousAuthenticationFilter.class) // Will handle authentication
                .authorizeHttpRequests (authorize ->
                        authorize
                                .requestMatchers ("/actuator/health").permitAll ()
//                                .requestMatchers (new AntPathRequestMatcher ("/auth/user/**")).permitAll ()
//                                .requestMatchers (new AntPathRequestMatcher ("/auth/token/**")).permitAll ()
//                                .requestMatchers (new AntPathRequestMatcher ("/auth/role/**")).permitAll ()
                                .anyRequest ().authenticated ()
                )
                // Disable unnecessary spring security features
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .build();
    }

    public AuthFIlter authFIlter(AuthenticationManager authenticationManager) throws Exception {
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/auth/user/**"),
                new AntPathRequestMatcher("/auth/token/**"),
                new AntPathRequestMatcher("/auth/role/**")
        );
        AuthFIlter authFIlter = new AuthFIlter(orRequestMatcher);
        authFIlter.setAuthenticationManager(authenticationManager);
        return authFIlter;
    }
}
