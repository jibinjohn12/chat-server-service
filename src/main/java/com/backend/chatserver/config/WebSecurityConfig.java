package com.backend.chatserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // This is not for websocket authorization, and this should most likely not be altered.
        http
                .httpBasic()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/js/**", "/lib/**", "/images/**", "/css/**", "/index.html", "/","/*.css","/webjars/**", "/*.js", "**/info/**").permitAll()
                .anyRequest().denyAll();
    }
}
