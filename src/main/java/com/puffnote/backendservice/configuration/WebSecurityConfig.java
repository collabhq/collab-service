package com.puffnote.backendservice.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;

/**
 * Created by sudeshgutta on 2019-04-06
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ArrayList allowedOriginsList = new ArrayList<String>();
        allowedOriginsList.add("http://localhost:3000");
        allowedOriginsList.add("http://localhost:5000");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(allowedOriginsList);
        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.applyPermitDefaultValues();

        http.cors().configurationSource(request -> corsConfiguration);
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }
}
