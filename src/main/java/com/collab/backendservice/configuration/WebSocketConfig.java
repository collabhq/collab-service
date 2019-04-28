package com.collab.backendservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

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

    // TODO: DELETE FOLLOWING COMMENTED CODE AND UNUSED IMPORTS AFTER CONFIRMING FIX
    /*
    @Override
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
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Principal user = null; // access authentication header(s)
                    accessor.setUser(user);
                }

                return message;
            }
        });
    }
    */
}

