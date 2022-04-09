package com.security.jwtcomp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    @Value("${jwt.secret}")
    private String secret;

    //Retrieve Username from jwt token
    public String extractToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //For retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //Check if Token has Expired
    private boolean isTokenExpired(String token) {
        final Date  expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Generate Token for User
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /* While Creating the Token
     * 1. Define claims of token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using HS512 algorithm and secret key
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    //Validate Token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractToken(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }
}
