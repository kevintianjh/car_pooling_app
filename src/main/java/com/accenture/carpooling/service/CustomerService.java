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
		return customerRepository.save(customer);
	};
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	};
	
	public Customer getCustomerbyId(Integer id) {
		return customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer", "Id", id));
	};
	
	public Customer updateCustomer(Customer customer, Integer id) {
		//check whether customer exists in the database
		Customer existingCustomer= customerRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Customer", "Id", id));
		
		existingCustomer.setName(customer.getName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setPhone(customer.getPhone());
		existingCustomer.setDob(customer.getDob());
		//save existing employee to DB
		customerRepository.save(existingCustomer);
		return existingCustomer;
		
	};
	
	
	public void deleteCustomer(Integer id) {
		//check whether the employee exist in database.
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee", "Id", id));
		
		customerRepository.delete(customer);
	};

}
