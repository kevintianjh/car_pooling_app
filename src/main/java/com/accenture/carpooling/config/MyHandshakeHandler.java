package com.accenture.carpooling.config;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;  
import com.accenture.carpooling.config.WebSocketConfig.MyPrincipal;
import com.accenture.carpooling.service.AuthenticationHandler;

@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler implements HandshakeInterceptor {
	
	@Autowired private AuthenticationHandler authenticationHandler;
	
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

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		Map<String, String> pairs = MyHandshakeHandler.splitQuery(new URL(request.getURI().toString()));
		String header_id = pairs.get("header_id");
		String header_role = pairs.get("header_role");
		String header_expiry = pairs.get("header_expiry");
		String header_signature = pairs.get("header_signature");
		
		return this.authenticationHandler.verifyRequest(header_role, header_id, header_expiry, header_signature); 
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {} 
	
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new HashMap<>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
}
