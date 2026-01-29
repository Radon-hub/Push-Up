package org.radon.pushup.shared.aop.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.radon.pushup.features.user.domain.Authority;
import org.radon.pushup.features.user.domain.Role;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.infrastructure.repository.entities.AuthorityEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.IllegalArgumentException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    @Value("${app.security.jwt.SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${app.security.jwt.ACCESS_TOKEN_VALIDITY_SECONDS}")
    private long ACCESS_TOKEN_VALIDITY_SECONDS;
    @Value("${app.security.jwt.REFRESH_TOKEN_VALIDITY_SECONDS}")
    private long REFRESH_TOKEN_VALIDITY_SECONDS;


    public String generateToken(User userDetails) {
        return tokenGenerator(userDetails, System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    public String generateRefreshToken(User userDetails) {
        return tokenGenerator(userDetails, System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    private String tokenGenerator(User userDetails,long expiration) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorization",userDetails.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public User extractUser(String token) {

        var body = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        // subject = phone
        String username = body.getSubject();

        Object roleClaim = body.get("authorization");

        if (!(roleClaim instanceof Map<?, ?> roleMap)) {
            throw new IllegalArgumentException("Invalid role claim");
        }

        // ---- role name ----
        String roleName = roleMap.get("role").toString();

        // ---- authorities ----
        Object authoritiesClaim = roleMap.get("authorities");

        if (!(authoritiesClaim instanceof List<?> authorityList)) {
            throw new IllegalArgumentException("Invalid authorities claim");
        }

        Set<Authority> authorities = authorityList.stream()
                .filter(Objects::nonNull)
                .map(item -> {
                    if(item instanceof Map<?, ?> authoritiesMap) {
                        Object value = authoritiesMap.get("authority");
                        if(value == null){
                            throw new IllegalArgumentException("Invalid authorities claim");
                        }
                        return new Authority(value.toString());
                    }
                    throw new IllegalArgumentException("Invalid authorities format in jwt");
                })
                .collect(Collectors.toSet());

        Role role = new Role(roleName, authorities);

        return new User.Builder().setUsername(username).setRole(role).build();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }



}
