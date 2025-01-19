package com.gaurav.projectmgmtsystem;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {
    public static final String SECRET_key = String.valueOf(Keys.secretKeyFor(SignatureAlgorithm.HS256));

    public static final String JWT_HEADER = "Authorization";



}
