package com.gg.cnt.security.jwt;

import com.gg.cnt.config.ApplicationConfigProperties;
import com.gg.cnt.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private static final String USER_ID = "userId";
    private static final String ROLES_KEY = "roles";
    private final Key key;
    private final JwtParser jwtParser;
    private final long tokenValidityInMilliseconds;

    public TokenProvider(ApplicationConfigProperties applicationConfigProperties) {
        ApplicationConfigProperties.Jwt jwtConfigProperties = applicationConfigProperties.getJwt();
        String secret = jwtConfigProperties.getSecretKey();
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = jwtConfigProperties.getExpiration();
    }

    public String createToken(final Long userId, final String username, final String roles) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .claim(USER_ID, userId)
                .claim(ROLES_KEY, roles)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(ROLES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .id(Long.parseLong(claims.get(USER_ID).toString()))
                .username(claims.getSubject())
                .password(null)
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(customUserDetails, token, authorities);
    }

    public boolean validateToken(final String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}
