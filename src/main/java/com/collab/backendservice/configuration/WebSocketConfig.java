package com.collab.backendservice.configuration;

import com.collab.backendservice.component.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    /**
     * Configures the STOMP web socket endpoint of service
     * @param registry Stomp Endpoint Registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //TODO: Fix setAllowedOrigins
        registry.addEndpoint("/collabsocket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * Configures a message broker
     * @param config Message Broker Registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic", "/queue");
    }

    /***
     * Authorises socket communication and sets the principal
     * @param registration ChannelRegistration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = Objects.requireNonNull(accessor.getNativeHeader("Authorization")).get(0);
                    AnonymousAuthenticationToken anonymousAuthenticationToken = jwtAuthorizationFilter.getAuthentication(token);
                    accessor.setUser(anonymousAuthenticationToken);
                }
                return message;
            }
        });
    }
}

