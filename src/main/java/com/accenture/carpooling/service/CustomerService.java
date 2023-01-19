package com.accenture.carpooling.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.repository.CustomerRepository;
import com.accenture.carpooling.exception.ResourceNotFoundException;
 
@Service
public class CustomerService {
	
	private CustomerRepository customerRepository;
	
	
	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}
	
	public Customer saveCustomer(Customer customer) {
		Customer customer1= new Customer();
		customer1.setEmail(customer.getEmail());
		customer1.setUsername(customer.getUsername());
		customer1.setName(customer.getName());
		customer1.setNewPassword(customer.getPassword());
		customer1.setPhone(customer.getPhone());
		customer1.setDob(customer.getDob());
		return customerRepository.save(customer1);
	};
	
	
	public Customer getCustomerbyId(Integer id) {
		return customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer", "Id", id));
	};

	

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	public void save(Customer newCustomer) {
		this.customerRepository.save(newCustomer);
	}
	
	public Customer findById(int customerId) {
		return this.customerRepository.findById(customerId).get();
	} 
}
