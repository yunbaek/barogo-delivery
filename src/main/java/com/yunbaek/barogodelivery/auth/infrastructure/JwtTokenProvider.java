package com.yunbaek.barogodelivery.auth.infrastructure;

import com.yunbaek.barogodelivery.auth.application.CustomUserDetailService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider<String, Authentication> {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private final CustomUserDetailService userDetailService;

    public JwtTokenProvider(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public String createToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            boolean b = !claims.getBody().getExpiration().before(new Date());
            return b;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails user = userDetailService.loadUserByUsername(getPayload(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String extractToken(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request);
    }


    private String getPayload(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}
