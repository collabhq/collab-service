package com.collab.backendservice.component;

import com.collab.backendservice.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sudeshgutta on 2019-04-27
 */
@Component
public class JwtTokenBuilder {
    public String buildJwtToken(String userUUID) {

        List roles = new ArrayList<>();
        roles.add("ROLE_USER");

        byte[] signingKey = Constants.JWT_SECRET.getBytes();
        String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", Constants.TOKEN_TYPE)
                .setIssuer(Constants.TOKEN_ISSUER)
                .setAudience(Constants.TOKEN_AUDIENCE)
                .setSubject(userUUID)
                .claim("rol", roles)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRY)).compact();
        return token;
    }
}
