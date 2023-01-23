package com.accenture.carpooling.service;
 
import org.springframework.stereotype.Service; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.CustomerLogin;
import com.accenture.carpooling.repository.CustomerLoginRepository;
import com.accenture.carpooling.repository.CustomerRepository; 
import jakarta.transaction.Transactional; 
import com.accenture.carpooling.exception.ResourceNotFoundException;
 
/* Author: Eugene, Kevin
 * Purpose: Business logic for CRUD functions of customer object
 */
@Service
public class CustomerService { 
	private CustomerRepository customerRepository; 
	private CustomerLoginRepository customerLoginRepository;
	
	public CustomerService(CustomerRepository customerRepository, CustomerLoginRepository customerLoginRepository) {
		super();
		this.customerRepository = customerRepository;
		this.customerLoginRepository = customerLoginRepository;
	}
	
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}; 
	
	public Customer getCustomerbyId(Integer id) {
		return customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer", "Id", id));
	};  

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	@Transactional
	public Customer saveNewCustomer(Customer newCustomer, CustomerLogin newCustomerLogin) {
		//Save Customer
		Customer savedCustomer = this.customerRepository.save(newCustomer);  
		
		//Save CustomerLogin  
		newCustomerLogin.setCustomer(savedCustomer);
		newCustomerLogin.setNewPassword(newCustomerLogin.getPassword());  
		this.customerLoginRepository.save(newCustomerLogin); 
		
		return savedCustomer;
	}
	
	public Customer findById(int customerId) {
		return this.customerRepository.findById(customerId).get();
	} 
}
