package com.accenture.carpooling.service; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  
import com.accenture.carpooling.entity.CustomerLogin;
import com.accenture.carpooling.repository.CustomerLoginRepository;

@Component
public class CustomerLoginService {
	@Autowired private CustomerLoginRepository customerLoginRepository;
	
	public CustomerLogin findByEmail(String email) {
		return this.customerLoginRepository.findByEmail(email).get();
	}
	
	public void save(CustomerLogin customerLogin) {
		this.customerLoginRepository.save(customerLogin);
	}
}
