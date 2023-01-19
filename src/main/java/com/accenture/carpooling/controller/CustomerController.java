package com.accenture.carpooling.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.service.CustomerService;
import com.accenture.carpooling.service.EmailService;

import jakarta.servlet.http.HttpServletRequest;
 
import jakarta.servlet.http.HttpServletRequest;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerController {
	
	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Customer> saveCustomer(HttpServletRequest request) throws ParseException {
		Customer newCustomer = new Customer();
		newCustomer.setName(request.getParameter("name"));
		newCustomer.setPhone(request.getParameter("phone"));
		newCustomer.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob")));
		newCustomer.setUsername(request.getParameter("username"));
		newCustomer.setEmail(request.getParameter("email"));
		newCustomer.setNewPassword(request.getParameter("password"));
		
		System.out.println(newCustomer);

		return new ResponseEntity<Customer>(customerService.saveCustomer(newCustomer), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<Customer> getCustomerbyId(@PathVariable("id") Integer customerId) {
		return new ResponseEntity<Customer>(customerService.getCustomerbyId(customerId), HttpStatus.OK);
	}
	
	
	@GetMapping("/test")
	public String test() {
		Customer newCustomer = new Customer();
		newCustomer.setDob(new Date());
		newCustomer.setEmail("tianjhenhaokevin@gmail.com");
		newCustomer.setName("Kevin");
		newCustomer.setNewPassword("password");
		newCustomer.setPhone("91993718");
		newCustomer.setUsername("kevintian");
		
		this.customerService.save(newCustomer);
		return "success";
	}
	
	@Autowired private EmailService emailService;
	
	@GetMapping("/test2")
	public String test2() {
		this.emailService.sendEmail("tianjhenhaokevin@gmail.com", "New Message from @kevin123", "Want to tompang?");
		
		return "SUCCESS!"; 
	}

}
