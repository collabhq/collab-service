package com.collab.backendservice.component;

import com.collab.backendservice.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sudeshgutta on 2019-04-27
 */
@Component
public class JwtTokenBuilder {

    @Value("${auth.jwt.secret}")
    private String JWT_SECRET;

    @Value("${auth.jwt.type}")
    private String TOKEN_TYPE;

    @Value("${auth.jwt.issuer}")
    private String TOKEN_ISSUER;

    @Value("${auth.jwt.audience}")
    private String TOKEN_AUDIENCE;

    @Value("${auth.jwt.expiryms}")
    private int TOKEN_EXPIRY;

    public String buildJwtToken(String userUUID) {
        List roles = new ArrayList<>();
        roles.add("ROLE_USER");

        byte[] signingKey = JWT_SECRET.getBytes();
        String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", TOKEN_TYPE)
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(userUUID)
                .claim("rol", roles)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY)).compact();
        return token;
    }
}
