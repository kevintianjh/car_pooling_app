package com.accenture.carpooling.service;

import java.util.ArrayList;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.Trip;
import com.accenture.carpooling.exception.ResourceNotFoundException; 
import com.accenture.carpooling.repository.TripRepository;

/* Author: Eugene, Kevin
 * Purpose: Business Logic for Trip CRUD functions.
 */
@Service
public class TripService {
	
	@Autowired private TripRepository tripRepository;
	@Autowired private CustomerService customerService;
	
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

	public List<Trip> getTripsWithSameDestination(String fromPostal, String toPostal,String days, String timeOfDay, String customerId) {
		String fromPostal1=fromPostal.substring(0,3);
		String toPostal1=toPostal.substring(0, 3);
		
		//insert '%' in between each char in 'days' string
		String newDays="%";
		for(int i=0;i<days.length();i++) {
			newDays += days.charAt(i) + "%";
		} 
		
		return tripRepository.getTripsWithSameDestination(fromPostal1, toPostal1, newDays, timeOfDay, customerId);
	};
	
	public Trip findById(Integer id) {
		return this.tripRepository.findById(id).get();
	}
	 
	public void save(Trip trip) {
		this.tripRepository.save(trip);
	}
	
	//Validate 'days' string to ensure that there is no duplicate in the string
	public boolean validateDaysString(String daysStr) { 
		ArrayList<Character> charList = new ArrayList<>();
		
		for(int i=0;i<daysStr.length();i++) {
			if(charList.contains(daysStr.charAt(i))) {
				return false;
			}
			else {
				charList.add(daysStr.charAt(i));
			}
		}
		
		return true; 
	} 
}
