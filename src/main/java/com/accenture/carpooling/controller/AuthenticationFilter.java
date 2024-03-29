package com.accenture.carpooling.controller;

import java.io.IOException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin; 
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.AuthenticationHandler;
import com.fasterxml.jackson.databind.ObjectMapper; 
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/* Author: Kevin Tian
 * Purpose: This filter will be executed on every http request to verify
 * the passed in parameters header_id, header_role, header_expiry and 
 * header_signature to check the integrity of the call 
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@CrossOrigin(origins="http://localhost:4200")
public class AuthenticationFilter implements Filter {  
	
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired private AuthenticationHandler authenticationHandler;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request; 
		String role = req.getParameter("header_role");
		String id = req.getParameter("header_id");
		String expiry = req.getParameter("header_expiry");
		String signature = req.getParameter("header_signature"); 
		String uri = req.getRequestURI(); 
		JsonResponseBase jsonRspBase = new JsonResponseBase();
		 
		if(uri.startsWith("/customer")) {
			if(!this.authenticationHandler.verifyRequest(role, id, expiry, signature) || !role.equals("customer")) {
				jsonRspBase.header_error="auth_failed";
				response.getWriter().println(objectMapper.writeValueAsString(jsonRspBase));
				return;
			}
		}  
		
		chain.doFilter(request, response);
	}   
}

