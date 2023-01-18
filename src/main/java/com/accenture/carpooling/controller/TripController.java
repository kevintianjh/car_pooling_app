package com.accenture.carpooling.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.Trip;
import com.accenture.carpooling.service.TripService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class TripController {
	
	private TripService tripService;
	
	public TripController(TripService tripService) {
		super();
		this.tripService = tripService;
	}
	
	@PostMapping("/addTrip/{customerId}")
	public ResponseEntity<Trip> saveTrip(@PathVariable("customerId") Integer customerId, @RequestBody Trip trip) {
		return new ResponseEntity<Trip>(tripService.saveTrip(customerId, trip), HttpStatus.CREATED);
	}
	
	@GetMapping("/getTrips")
	public List<Trip> getAllTrips() {
		return tripService.getAllTrips();
	}
	

	@GetMapping("/trip/list")
	public List<Trip> getTripsByCustomerId(@RequestParam("header_id") Integer customerId) {
		return tripService.getTripsByCustomerId(customerId);
	} 
	
	@GetMapping("/trip/getTripSameDest/{fromPost}/{toPost}")
	public List<Trip> getTripsWithSameDestination(@PathVariable("fromPost") String fromPostal, @PathVariable("toPost") String toPostal) {
		return tripService.getTripsWithSameDestination(fromPostal, toPostal);
	} 
	
	@PutMapping("/updateTrip/{id}")
	public ResponseEntity<Trip> updateTrip(@PathVariable("id") Integer tripId, @RequestBody Trip trip){
		return new ResponseEntity<Trip>(tripService.updateTrip(trip, tripId), HttpStatus.OK);
		
	}
	
	@DeleteMapping({"/deleteTrip/{id}"})
	public ResponseEntity<String> deleteTrip(@PathVariable("id") Integer id) {
		tripService.deleteTrip(id);
		return new ResponseEntity<String>("Trip Deleted Successfully", HttpStatus.OK);
		
	}
	

}
