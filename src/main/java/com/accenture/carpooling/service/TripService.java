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
	@Autowired private CustomerService customerService;
	
	public TripService(TripRepository tripRepository) {
		super();
		this.tripRepository = tripRepository;
	}
	
	public List<Trip> getTripsByCustomerId (Integer customerId) { 
		return this.tripRepository.getTripsByCustomerId(customerId); 
	} 
	
	public Trip saveTrip(Integer customerId, Trip trip) {
		
		Customer customer=customerService.getCustomerbyId(customerId);
		trip.setCustomer(customer);
		return tripRepository.save(trip);
	};
	
	public Trip getTripbyId(Integer customerId) {
		return tripRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Trip", "Id", customerId));
	};
	
	public Trip updateTrip(Trip trip, Integer id) {
		//check whether customer exists in the database
		Trip existingTrip= tripRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Trip", "Id", id));
		
		existingTrip.setFromPostal(trip.getFromPostal());
		existingTrip.setToPostal(trip.getToPostal());
		existingTrip.setRole(trip.getRole()); 
		existingTrip.setDescription(trip.getDescription());
		//save existing employee to DB
		tripRepository.save(existingTrip);
		return existingTrip;
		
	}; 
	
	public void deleteTrip(Trip trip) {
		this.tripRepository.delete(trip);
	};
	


	
	public void deleteTrip(Integer id) {
		//check whether the employee exist in database.
		Trip trip = tripRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee", "Id", id));
		
		tripRepository.delete(trip);
	}

	

	public List<Trip> getTripsWithSameDestination(String fromPostal, String toPostal,String day, String timeOfDay) {
		String fromPostal1=fromPostal.substring(0,3);
		String toPostal1=toPostal.substring(0, 3);
		return tripRepository.getTripsWithSameDestination(fromPostal1, toPostal1, day, timeOfDay);
	};
	
	public Trip findById(Integer id) {
		return this.tripRepository.findById(id).get();
	}
	 
	public void save(Trip trip) {
		this.tripRepository.save(trip);
	}

}
