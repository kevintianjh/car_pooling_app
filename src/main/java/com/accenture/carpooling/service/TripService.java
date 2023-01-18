package com.accenture.carpooling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.Trip;
import com.accenture.carpooling.exception.ResourceNotFoundException;
import com.accenture.carpooling.repository.CustomerRepository;
import com.accenture.carpooling.repository.TripRepository;

@Service
public class TripService {
	
	@Autowired private TripRepository tripRepository;
	
	public TripService(TripRepository tripRepository) {
		super();
		this.tripRepository = tripRepository;
	}
	
	public Page <Trip> getTripsByCustomerId ( Integer customerId) {
		Pageable pageable = Pageable.ofSize(10);
		return this.tripRepository.getTripsByCustomerId(pageable, customerId);
		
	}
	

	
	public Trip saveTrip(Trip trip) {
		Customer customer=trip.getCustomer();
		return tripRepository.save(trip);
	};
	
	public List<Trip> getAllTrips() {
		return tripRepository.findAll();
	};
	
	public Trip getTripbyId(Integer id) {
		return tripRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Trip", "Id", id));
	};
	
	public Trip updateTrip(Trip trip, Integer id) {
		//check whether customer exists in the database
		Trip existingTrip= tripRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Trip", "Id", id));
		
		existingTrip.setFromPostal(trip.getFromPostal());
		existingTrip.setToPostal(trip.getToPostal());
		existingTrip.setRole(trip.getRole());
		existingTrip.setTags(trip.getTags());
		existingTrip.setDescription(trip.getDescription());
		//save existing employee to DB
		tripRepository.save(existingTrip);
		return existingTrip;
		
	};
	
	
	public void deleteTrip(Integer id) {
		//check whether the employee exist in database.
		Trip trip = tripRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee", "Id", id));
		
		tripRepository.delete(trip);
	};
	


}
