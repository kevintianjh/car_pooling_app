package com.accenture.carpooling.repository;

import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;  
import com.accenture.carpooling.entity.Trip;


public interface TripRepository extends JpaRepository<Trip, Integer>{

	@Query("select t from Trip t where t.customer.id=?1")
	public List<Trip> getTripsByCustomerId(Integer customerId);
	
	@Query("select t from Trip t WHERE SUBSTRING(t.fromPostal, 1, 3)=?1 AND SUBSTRING(t.toPostal, 1, 3)=?2 AND t.days LIKE ?3 AND t.timeOfDay LIKE %?4% AND t.customer.id<>?5")
	public List<Trip> getTripsWithSameDestination(String fromPostal,String toPostal, String day, String timeOfDay, String customerId);
}