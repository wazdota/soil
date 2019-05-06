package com.soil.gateway.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public List<SimpleGrantedAuthority> getAuthorityFromToken(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            @SuppressWarnings("unchecked")
            List<String> authorities = claims.get("authorities",List.class);
            return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public int getUserIdFromToken(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            String id = claims.getId();
            return Integer.parseInt(id);
        } catch (Exception e) {
            return 0;
        }
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
