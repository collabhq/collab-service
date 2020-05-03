package com.collab.backendservice.component;

import com.collab.backendservice.model.User;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.util.Constants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sudeshgutta on 2019-04-27
 */
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    ServletContext servletContext;

    @Value("${auth.jwt.secret}")
    private String JWT_SECRET;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(Constants.TOKEN_HEADER);
        if (StringUtils.isEmpty(header) || !header.startsWith(Constants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(request.getHeader(Constants.TOKEN_HEADER)));
        filterChain.doFilter(request, response);
    }

    /**
     * Gets an anonymous auth token for the given JWT
     * @param token Token
     * @return AnonymousAuthenticationToken
     */
    public AnonymousAuthenticationToken getAuthentication(String token) {
        AnonymousAuthenticationToken anonymousAuthenticationToken = null;
        if (!StringUtils.isEmpty(token)) {
            try {
                byte[] signingKey = JWT_SECRET.getBytes();
                Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", "")).getBody();
                String userUUID = claims.getSubject();

                List authorities = ((List<?>) claims.get("rol"))
                        .stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                if (userService == null) {
                    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                    userService = Objects.requireNonNull(webApplicationContext).getBean(UserService.class);
                }

                User user = userService.findByUuid(userUUID);
                if (user != null) {
                    anonymousAuthenticationToken = new AnonymousAuthenticationToken(userUUID, user, authorities);
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }
        return anonymousAuthenticationToken;
    }
}
