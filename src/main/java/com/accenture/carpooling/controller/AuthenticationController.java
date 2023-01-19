package com.accenture.carpooling.controller;

import jakarta.servlet.http.HttpServletRequest; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.AuthenticationHandler;
import com.accenture.carpooling.service.CustomerService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException; 

@RestController
@CrossOrigin
public class AuthenticationController { 
	
	static class JsonResponse extends JsonResponseBase {}  
	 
	@Autowired private AuthenticationHandler authenticationHandler;  
	@Autowired private CustomerService customerService;
	 
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
		
		Customer customer = null;  
		
		try {
			customer = this.customerService.findByEmail(email); 
		}
		catch(EmptyResultDataAccessException e) {}
		
		if(customer == null || !customer.isPasswordMatch(password)) {
			return jsRsp;
		} 
		  
		jsRsp.header_rsp = "ok";
		jsRsp.header_expiry = this.authenticationHandler.generateUpdatedExpiry();
		jsRsp.header_role = role;
		jsRsp.header_id = String.valueOf(customer.getId()); 
		jsRsp.header_signature = this.authenticationHandler.generateSignature(jsRsp.header_role, String.valueOf(customer.getId()), jsRsp.header_expiry);
		
		return jsRsp;  
	}  
	
	@RequestMapping("/test")
	public String test() {
		Customer cust = this.customerService.findByEmail("kevin@gmail.com");
		cust.setNewPassword("password");
		cust.setUsername("kevin123");
		cust.setDob(new Date());
		cust.setPhone("91993718");
		this.customerService.save(cust);
		return "HEHE!";
	}
} 