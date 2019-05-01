package com.collab.backendservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by karthik on 2019-04-28
 */
@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class EnvironmentConfig{

    @Value("${auth.jwt.secret}")
    private static String JWT_SECRET;

    @Value("${auth.jwt.type}")
    private String TOKEN_TYPE;

    @Value("${auth.jwt.issuer}")
    private String TOKEN_ISSUER;

    @Value("${auth.jwt.audience}")
    private String TOKEN_AUDIENCE;

    @Value("${auth.jwt.expiryms}")
    private int TOKEN_EXPIRY;

    //Bean needed to resolve ${property.name} syntax
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}