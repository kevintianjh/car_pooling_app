package com.accenture.carpooling.controller;

import java.text.ParseException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RestController; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.CustomerLogin; 
import com.accenture.carpooling.service.CustomerService; 
import jakarta.servlet.http.HttpServletRequest; 
 
/* Author: Kevin Tian
 * Purpose: Business logic for Customer entity 
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerController {
	
	@Autowired private CustomerService customerService; 
	
	@PostMapping("/register")  
	public ResponseEntity<Customer> saveCustomer(HttpServletRequest request,
												 Customer newCustomer,
												 CustomerLogin newCustomerLogin) throws ParseException {
		
		newCustomer.setDob(request.getParameter("dob_string")); 
		Customer savedCustomer = this.customerService.saveNewCustomer(newCustomer, newCustomerLogin); 
		return new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
	} 
	
	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<Customer> getCustomerbyId(@PathVariable("id") Integer customerId) {
		return new ResponseEntity<Customer>(customerService.getCustomerbyId(customerId), HttpStatus.OK);
	}  
}
