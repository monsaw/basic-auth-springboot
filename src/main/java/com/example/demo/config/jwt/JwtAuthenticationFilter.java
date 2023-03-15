package com.example.demo.config.jwt;

import com.example.demo.config.LibraryUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final LibraryUserDetailsService libraryUserDetailsService;

    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
        String token = null;
        String userName = null;
        String authHeader = request.getHeader("Authorization");
            System.out.println(">>>> " + authHeader);
            log.debug(">>>>>> username : >>>>>: {}",authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtUtils.extractUserNameFromToken(token);
            log.trace(">>>>>> username : >>>>>: {}",userName);
            System.out.println(">>>> " + userName);

        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = libraryUserDetailsService.loadUserByUsername(userName);
            log.trace(">>>>>> userDetails : >>>>>: {}",userDetails);
            System.out.println(">>>> " + userDetails.getUsername());
            if(jwtUtils.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.trace(">>>>>> security context : >>>>>: {}",SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
    filterChain.doFilter(request,response);

    } catch (JwtException e){
            log.trace("Big issues");
            log.debug("bud ffh");
        }
    }
}
