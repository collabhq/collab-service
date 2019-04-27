package com.collab.backendservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.*;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

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

    /*@Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        // You can customize your authorization mapping here.
        messages.simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT, HEARTBEAT)
                .permitAll()
                .simpDestMatchers("/app/**", "/topic/**").authenticated()
                .simpSubscribeDestMatchers("/topic/**").authenticated();
    }

    // TODO: Should re-enable this and provide a CRSF endpoint.
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }*/
}

