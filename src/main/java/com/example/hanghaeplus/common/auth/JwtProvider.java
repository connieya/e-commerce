package com.example.hanghaeplus.common.auth;


import com.example.hanghaeplus.domain.user.UserTokens;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(@Value("${security.jwt.secret-key}") final String secretKey, @Value("${security.jwt.access-expiration-time") final Long accessExpirationTime, @Value("${security.jwt.refresh-expiration-time}") final Long refreshExpirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public UserTokens generateToken(final String subject) {
        final String refreshToken = createToken("",refreshExpirationTime);
        final String accessToken = createToken(subject,accessExpirationTime);
        return new UserTokens(refreshToken,accessToken);
    }

    private String createToken(final String subject , final  Long validityInMilliseconds){
        final Date now = new Date();
        final  Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
