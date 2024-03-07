package com.backend.chatserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final String endPoint;
    private final String destPrefix;
    private final String brokerPrefix;

    public WebSocketConfig(@Value("${app.websocket.endpoint}") String endPoint,
                           @Value("${app.websocket.dest-prefix}") String destPrefix,
                           @Value("${app.websocket.broker-prefix}") String brokerPrefix) {
        this.endPoint = endPoint;
        this.destPrefix = destPrefix;
        this.brokerPrefix = brokerPrefix;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(this.endPoint)
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint(this.endPoint)
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(this.destPrefix);
        registry.enableSimpleBroker(this.brokerPrefix);
    }
}
