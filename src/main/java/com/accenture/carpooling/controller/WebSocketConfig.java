package com.accenture.carpooling.controller;
 
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map; 
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {  
	
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
    .setHandshakeHandler(new MyHandshakeHandler())  
    .setAllowedOrigins("http://localhost:4200", "http://localhost:4200/") 
    .withSockJS();
  }
  
  public class MyHandshakeHandler extends DefaultHandshakeHandler {
	 
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		
		try {
			Map<String, String> pairs = MyHandshakeHandler.splitQuery(new URL(request.getURI().toString()));
			MyPrincipal principal = new MyPrincipal(pairs.get("header_id"));
			return principal;
		}
		catch(Exception e) {
			return null;
		}  
	}
	
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	} 
  } 
}