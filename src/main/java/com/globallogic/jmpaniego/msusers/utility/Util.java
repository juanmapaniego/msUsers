package com.globallogic.jmpaniego.msusers.utility;

import com.globallogic.jmpaniego.msusers.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Component
public class Util {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Util(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(user.getLastLogin().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(user.getLastLogin().plus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(SignatureAlgorithm.HS256,"thisisatestingkeyisatestingkeyforhs256THISISATESTINGKEYISATESTINGKEYFORHS256")

                .compact();
    }
}
