package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.demo.config.Endpoints.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().
                authorizeHttpRequests().
                requestMatchers(UNSECURED_ENDPOINT).
                permitAll().
                and().authorizeHttpRequests().
                requestMatchers(SECURED_ENDPOINT).
                hasAuthority("ADMIN")
                .requestMatchers(SECURED_ENDPOINT_USER).
                hasAuthority("USER").
                anyRequest().
                authenticated()
                .and().
                httpBasic().and().
                build();
    }
}
