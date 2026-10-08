package br.com.lsargus.testesicredi.security;

import br.com.lsargus.testesicredi.infrastruct.entity.UserAccountEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties properties;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                properties.secret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(UserAccountEntity user){

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(
                        Date.from(
                                now.plusMillis(
                                        properties.expiration()
                                )
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUserId(String token) {
        return parseToken(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValid(String token){
        try {
            parseToken(token);
            return true;

        } catch (JwtException |
                 IllegalArgumentException _){
            return false;
        }
    }

    private Jws<Claims> parseToken(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
    }

    public Long getExpirationSeconds() {
        return properties.expiration();
    }
}
