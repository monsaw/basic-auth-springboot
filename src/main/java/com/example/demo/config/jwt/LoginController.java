package com.example.demo.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        if(authentication.isAuthenticated())
        return jwtUtils.getGeneratedToken(loginRequest.getEmail());
        else {
            return "failed to authenticated";
        }
    }
}
