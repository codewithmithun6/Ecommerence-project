package com.codewithmithun.ecommerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET = "mySecretKeyForJwtTokenGeneration12345678901234567890";


    public String generateToken(String userName){
        HashMap<String, Object> claims = new HashMap<>();

        return createToken(claims,userName);

    }

    private String createToken(HashMap<String, Object> claims, String userName) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keybytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String extractUserName(String token){

        return extractClaim(token, Claims::getSubject);

    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {

        final Claims claims = extactAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extactAllClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token,Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
