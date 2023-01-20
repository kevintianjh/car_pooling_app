package com.accenture.carpooling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import com.accenture.carpooling.entity.Customer;
/* Author: Eugene
 * Purpose: Additional of business Logic to find Customer by email. 
 * Extends JPA Repository CRUD functions
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	public Customer findByEmail(String email);
}
