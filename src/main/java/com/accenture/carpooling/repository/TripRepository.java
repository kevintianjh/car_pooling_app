package com.accenture.carpooling.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.accenture.carpooling.entity.Trip;


public interface TripRepository extends JpaRepository<Trip, Integer>{

	@Query("select t from Trip t where t.customer.id=?1")
	public List<Trip> getTripsByCustomerId(Integer customerId); 
		
//	Page<Trip> findByPostalFromContaining(@Param("from_postal") String fromPostal, Pageable pageable );
	
}
