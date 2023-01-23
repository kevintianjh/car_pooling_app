package com.accenture.carpooling.controller;

import jakarta.servlet.http.HttpServletRequest; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;  
import com.accenture.carpooling.entity.CustomerLogin;
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.AuthenticationHandler;
import com.accenture.carpooling.service.CustomerLoginService;  
import java.util.NoSuchElementException; 
import org.springframework.beans.factory.annotation.Autowired; 

/* Author: Kevin Tian
 * Purpose: To authenticate users
 */
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AuthenticationController { 
	
	static class JsonResponse extends JsonResponseBase {}  
	 
	@Autowired private AuthenticationHandler authenticationHandler;  
	@Autowired private CustomerLoginService customerLoginService;
	 
	@RequestMapping("/authenticate")  
	public @ResponseBody JsonResponse m1(HttpServletRequest req) {
		 
		JsonResponse jsRsp = new JsonResponse(); 
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		
		if(email == null || password == null || role == null || !(role.equals("admin") || role.equals("customer"))) {
			return jsRsp;
		}
		
		if(email.length() > 30 || password.length() > 20) {
			return jsRsp;
		}
		
		CustomerLogin customerLogin = null;
		
		try {
			customerLogin = this.customerLoginService.findByEmail(email); 
		}
		catch(NoSuchElementException e) {}
		
		if(customerLogin == null || !customerLogin.isPasswordMatch(password)) {
			return jsRsp;
		}  
		  
		jsRsp.header_rsp = "ok";
		jsRsp.header_expiry = this.authenticationHandler.generateUpdatedExpiry();
		jsRsp.header_role = role;
		jsRsp.header_id = String.valueOf(customerLogin.getCustomer().getId()); 
		jsRsp.header_signature = this.authenticationHandler.generateSignature(jsRsp.header_role, jsRsp.header_id, jsRsp.header_expiry);
		
		return jsRsp;  
	}   
} 