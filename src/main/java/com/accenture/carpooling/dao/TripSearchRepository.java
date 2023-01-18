package com.accenture.carpooling.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.accenture.carpooling.entity.Trip;




@RepositoryRestResource
public interface TripSearchRepository extends JpaRepository<Trip, Integer>{
	
	
	Page<Trip> findByCustomerId(@Param("id") Integer id, Pageable pageable );


}
