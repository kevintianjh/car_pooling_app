package com.accenture.carpooling.service;
 
import org.springframework.stereotype.Service; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.repository.CustomerRepository;
import com.accenture.carpooling.exception.ResourceNotFoundException;
 
/* Author:Eugene , Kevin
 * Purpose: Business logic for CRUD functions of customer object
 */
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
