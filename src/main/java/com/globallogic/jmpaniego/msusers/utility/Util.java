package com.globallogic.jmpaniego.msusers.utility;

import com.globallogic.jmpaniego.msusers.error.exception.InvalidTokenException;
import com.globallogic.jmpaniego.msusers.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class Util {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    public Util(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public String generateToken(User user){
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(user.getLastLogin().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(user.getLastLogin().plus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKeySpec)
                .compact();
    }

    public void validateToken(String token){
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();
        try {
            jwtParser.parse(token);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
        Date expiration = jwtParser.parseClaimsJws(token).getBody().getExpiration();
        if(expiration.before(new Date()))
            throw new InvalidTokenException("Expired token");
    }

    public String getEmail(String token){
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secretKeySpec)
                .build();
        try {
            System.out.println();
            return jwtParser.parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
}
