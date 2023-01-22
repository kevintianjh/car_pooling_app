package com.accenture.carpooling.config;
  
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration; 
import org.springframework.messaging.simp.config.MessageBrokerRegistry; 
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
  
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {  
	
  @Autowired private MyHandshakeHandler myHandshakeHandler;
	
  public static class MyPrincipal implements Principal {  
	private String name;
	
	public MyPrincipal(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	} 
  }
 
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/client");
    config.setApplicationDestinationPrefixes("/server");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
    .addEndpoint("/websocket")
    .addInterceptors(this.myHandshakeHandler)
    .setHandshakeHandler(this.myHandshakeHandler)  
    .setAllowedOrigins("http://localhost:4200", "http://localhost:4200/") 
    .withSockJS();
  } 
}