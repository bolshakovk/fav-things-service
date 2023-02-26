package com.bolshakovk.favthingsservice.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.Jwts.claims;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.header}")
    private String authorisationHeader;
    @Value("${jwt.expiration}")
    private Long validityInMs;


    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        System.out.println("secret key in post construct: " + secretKey);
    }
    public String createToken(String username, String role){
        Claims claims = claims().setSubject(username); // мапа, в которую накидываются поля, для джвт токена
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMs * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.ES256,secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        System.out.println("token is valid:  " + !claimsJws.getBody().getExpiration().before(new Date()));
        return !claimsJws.getBody().getExpiration().before(new Date());
    }
    public Authentication getAuthentifocation(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        System.out.println("in get authentifocation: " + new UsernamePasswordAuthenticationToken(userDetails, userDetails.getUsername(), userDetails.getAuthorities()));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUsername(String token){
        System.out.println("get username via token: " + Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(authorisationHeader);
    }
}
