package com.example.demo.config.jwt;


import com.example.demo.config.LibraryUserDetails;
import com.example.demo.config.LibraryUserDetailsService;
import com.example.demo.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final LibraryUserDetailsService libraryUserDetailsService;
    @Value("${spring.secret.key}")
    private  String SECRET_KEY;

    @Value("${spring.expiration.time:18000000}")
    private Long JWT_EXPIRED_TIME;


    public String getGeneratedToken(String userName){
        UserDetails user = libraryUserDetailsService.loadUserByUsername(userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName" , "Monsuru");
        claims.put("lastName", "Lawal");
        claims.put("role",user.getAuthorities());
        return generateTokenForUser(claims, userName);
    }

    private String generateTokenForUser(Map<String, Object> claims, String userName) {
       return
        Jwts.builder().setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED_TIME) )
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
               .compact();
   }

    private Key getSignKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserNameFromToken(String token){
            return extractClaim(token,Claims::getSubject);
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().
                setSigningKey(getSignKey()).build().parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generatePasswordResetToken(String email){
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_EXPIRED_TIME);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignKey(),SignatureAlgorithm.HS512)
                .compact();


    }

    }
