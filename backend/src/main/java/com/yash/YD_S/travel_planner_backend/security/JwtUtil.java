package com.yash.YD_S.travel_planner_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private Key key;

    /**
     * Initialize the signing key once (lazily).
     */
    private Key getSigningKey() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        }
        return key;
    }

    /**
     * Generate a signed JWT with subject = username
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtExpirationMs);

        Date issuedAt = Date.from(now);
        Date expirationDate = Date.from(expiry);

        ZonedDateTime localExpiry = expiry.atZone(ZoneId.systemDefault());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        log.debug("Generated JWT for {}: expires at {} UTC ({} local)",
                username, expiry, localExpiry);

        return token;
    }

    /**
     * Extract username (subject) from a valid token
     */
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Get expiration date from token (in UTC)
     */
    public Date getExpirationDateFromToken(String token) {
        return parseClaims(token).getExpiration();
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            Date now = new Date();
            return expiration.before(now);
        } catch (Exception e) {
            log.warn("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }


    /**
     * Validate signature and expiration of JWT
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = parseClaims(token);

            Date expiration = claims.getExpiration();
            Date now = new Date();
            ZonedDateTime localExpiry = expiration.toInstant().atZone(ZoneId.systemDefault());
            ZonedDateTime localNow = now.toInstant().atZone(ZoneId.systemDefault());

            log.debug("Token validation - Current time: {} UTC ({} local)",
                    now.toInstant(), localNow);
            log.debug("Token validation - Expires at: {} UTC ({} local)",
                    expiration.toInstant(), localExpiry);

            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("JWT malformed: {}", e.getMessage());
        } catch (SignatureException e) {
            log.warn("JWT signature invalid: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}