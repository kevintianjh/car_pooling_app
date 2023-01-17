package com.accenture.carpooling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.carpooling.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
