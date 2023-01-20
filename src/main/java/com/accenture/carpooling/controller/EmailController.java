package com.accenture.carpooling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.CustomerService;
import com.accenture.carpooling.service.EmailService; 
import jakarta.servlet.http.HttpServletRequest;

/* Author: Eugene
 * Purpose: To handle email sending
 */
@RestController
@CrossOrigin
@RequestMapping("/customer")
public class EmailController { 
	
	@Autowired private CustomerService customerService;
	@Autowired private EmailService emailService;
	
	@PostMapping("/send-email")
	public JsonResponseBase m1(HttpServletRequest request) { 
		Integer customerId = Integer.parseInt(request.getParameter("header_id"));
		Integer toCustomerId = Integer.parseInt(request.getParameter("to_id"));
		String message = request.getParameter("message");
		
		Customer customer = this.customerService.findById(customerId);
		Customer toCustomer = this.customerService.findById(toCustomerId);
		
		System.out.println("HELLO " + toCustomer);
		
		//Hardcoded mailTo as static during development stage
		String mailTo = "eugene91@msn.com";
		String subject = "Tompang: Message from @" + customer.getUsername(); 
		
		this.emailService.sendEmail(mailTo, subject, message);
		JsonResponseBase jsRsp = new JsonResponseBase();
		jsRsp.header_rsp = "ok";
		
		return jsRsp;
	}
}
