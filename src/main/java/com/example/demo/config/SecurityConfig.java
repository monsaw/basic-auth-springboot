package com.example.demo.config;

import com.example.demo.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.demo.config.Endpoints.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final LibraryUserDetailsService libraryUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
                // from here we use jwt
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).
 // removing basic auth to add jwt
//                httpBasic().and().
                build();
    }
    @Bean
    public AuthenticationManager authentication(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager() ;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(libraryUserDetailsService);
        return auth;
    }
}
