package com.collab.backendservice.configuration;

import com.collab.backendservice.component.JwtAuthorizationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;

/**
 * Created by sudeshgutta on 2019-04-06
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Force HTTPS
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();

        // CORS
        ArrayList<String> allowedOriginsList = new ArrayList<>();
        allowedOriginsList.add("http://localhost:3000");
        allowedOriginsList.add("https://collabhq.app");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(allowedOriginsList);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.applyPermitDefaultValues();

        http.cors().configurationSource(request -> corsConfiguration);
        http.csrf().disable().authorizeRequests()
                .antMatchers("/workspace", "/workspace/{identifier}", "/collabsocket/**").permitAll()
                .anyRequest().hasAnyRole("USER")
                .and()
                .addFilterAfter(new JwtAuthorizationFilter(),  AnonymousAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
