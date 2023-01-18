package com.accenture.carpooling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import com.accenture.carpooling.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	@Query("SELECT c FROM Customer c WHERE c.email=?1")
	public Customer findByEmail(String email);
}
