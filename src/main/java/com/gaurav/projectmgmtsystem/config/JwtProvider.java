package com.gaurav.projectmgmtsystem.config;

import com.gaurav.projectmgmtsystem.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {


    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_key.getBytes()); // Use secure key generation

    // Generate token method
    public String generateToken(Authentication auth) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours expiration
                .claim("email", auth.getName())  // Store the email as a claim with key "email"
                .signWith(key)  // Sign with the secure key
                .compact();
    }

    // Extract email from JWT token
    public static String getEmailFromToken(String jwt) {
        System.out.println(jwt);
        jwt = jwt.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)  // Use the same secret key to parse the JWT
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get("email", String.class);  // Get the email claim
    }
}
