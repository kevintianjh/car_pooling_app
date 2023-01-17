package com.accenture.carpooling.controller;

import jakarta.servlet.http.HttpServletRequest; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.AuthenticationHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException; 

@RestController
@CrossOrigin
public class AuthenticationController { 
	
	static class JsonResponse extends JsonResponseBase {}  
	
	@Autowired private AuthenticationHandler authenticationHandler;  
	 
	@RequestMapping("/authenticate")  
	public @ResponseBody JsonResponse m1(HttpServletRequest req) {
		JsonResponse jsRsp = new JsonResponse();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		
		if(username == null || password == null || role == null || !(role.equals("admin") || role.equals("customer"))) {
			return jsRsp;
		}
		
		if(username.length() > 20 || password.length() > 20) {
			return jsRsp;
		}
		
		//User loginUser = null;
		
		try {
			//loginUser = 
		}
		catch(EmptyResultDataAccessException e) {}
		
		 
		  
		jsRsp.header_rsp = "ok";
		jsRsp.header_expiry = this.authenticationHandler.generateUpdatedExpiry();
		jsRsp.header_role = role;
		jsRsp.header_username = username; 
		jsRsp.header_signature = this.authenticationHandler.generateSignature(jsRsp.header_role, username, jsRsp.header_expiry);
		
		return jsRsp; 
	} 
	 
} 