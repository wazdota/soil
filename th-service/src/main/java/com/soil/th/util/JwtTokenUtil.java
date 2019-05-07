package com.soil.th.util;

import io.jsonwebtoken.Claims;
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
public class JwtTokenUtil implements Serializable{
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

    public String getAuthorityFromToken(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.get("authorities",String.class);
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
