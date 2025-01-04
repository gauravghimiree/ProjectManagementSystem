package com.gaurav.projectmgmtsystem.config;

import com.gaurav.projectmgmtsystem.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Use secure key generation

    // Generate token method
    public String generateToken(Authentication auth) {
        // Set the expiration time to 24 hours (86400000 ms)
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours expiration
                .claim("email", auth.getName())  // Store the email as a claim
                .signWith(key)  // Sign with the secure key
                .compact();
    }

    // Extract email from JWT token
    public static String getEmailFromToken(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)  // Use the same secret key to parse the JWT
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get("email", String.class);  // Get the email claim
    }
}
