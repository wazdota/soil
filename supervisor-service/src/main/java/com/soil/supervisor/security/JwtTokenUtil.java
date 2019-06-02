package com.soil.supervisor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "admin");
        claims.put("id", 1);
        claims.put("authorities", "ROLE_SUPER");
        claims.put("created", new Date());
        return generateToken(claims);
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
            String authority = claims.get("authorities",String.class);
            List<SimpleGrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority(authority));
            return list;
           // @SuppressWarnings("unchecked")
           // List<String> authorities = claims.get("authorities",List.class);
            //log.info(String.join(String.join("",authorities)));
           // return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public int getUserIdFromToken(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.get("id",Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
