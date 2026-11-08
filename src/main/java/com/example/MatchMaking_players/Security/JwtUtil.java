package com.example.MatchMaking_players.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {
    private  static  final  long JWT_TOKEN_VALIDITY = 30L*24*60*60*1000;

    @Value("${jwt.secret:TheTalentIsn'tRequestTogrowLikeDeveloper}")
    private  String secret;
    public SecretKey getSingingkey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String getUsernameFrontToken(String token){
        return getClaimFronToken(token, Claims::getSubject);
    }

    private <T> T getClaimFronToken(String token, Function<Claims,T> claimResolver) {
        final Claims claims=getAllClaimFrontToken(token);
        return  claimResolver.apply(claims);
    }

    private Claims getAllClaimFrontToken(String token) {
        return Jwts.parser().verifyWith(getSingingkey()).build().parseSignedClaims(token).getPayload();
    }

    private  Boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        return  expiration.before(new Date());
    }

    public  String generateToken(String username,String  role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role",role);
        return doGenerateToken(claims,username);
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims).subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
                .signWith(getSingingkey())
                .compact();
    }
    public Boolean validateToken(String token){
        try {
            getAllClaimFrontToken(token);
            return !isTokenExpired(token);
        }catch (Exception e){
            System.out.println("JWT validation error: "+ e.getMessage());
            return false;
        }
    }
    private Date getExpirationDateFromToken(String token) {
        return getClaimFronToken(token,Claims::getExpiration);
    }

    public String getRoleFromToken(String token){
        return  getClaimFronToken(token,claims -> claims.get("role", String.class));
    }
}
