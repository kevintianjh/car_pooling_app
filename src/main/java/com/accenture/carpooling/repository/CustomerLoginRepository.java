package com.accenture.carpooling.repository;

import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.CustomerLogin;

public interface CustomerLoginRepository extends JpaRepository<CustomerLogin, Integer> {
	
	@Query("SELECT cl FROM CustomerLogin cl WHERE cl.customer.email=?1")
	public Optional<CustomerLogin> findByEmail(String email);
}
