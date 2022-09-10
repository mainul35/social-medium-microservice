package com.mainul35.auth.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement ()
                .sessionCreationPolicy (SessionCreationPolicy.STATELESS) // We don't need to create any session
                .and ()
                .authenticationProvider (authenticationProvider)
                .addFilterBefore (authFIlter (), AnonymousAuthenticationFilter.class) // Will handle authentication
                .authorizeRequests ()
                .antMatchers ("/actuator/health").permitAll ()
                .anyRequest()
                .authenticated()
                .and()
                // Disable unnecessary spring security features
                .csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .cors();
    }

    public AuthFIlter authFIlter() throws Exception {
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/user/**"),
                new AntPathRequestMatcher("/token/**"),
                new AntPathRequestMatcher("/role/**")
        );
        AuthFIlter authFIlter = new AuthFIlter(orRequestMatcher);
        authFIlter.setAuthenticationManager(authenticationManager());
        return authFIlter;
    }
}
