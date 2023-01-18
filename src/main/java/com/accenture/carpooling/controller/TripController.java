package com.accenture.carpooling.controller;

import java.util.List; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 
import com.accenture.carpooling.entity.Trip;
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.TripService; 
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class TripController { 
	private TripService tripService;
	
	public TripController(TripService tripService) {
		super();
		this.tripService = tripService;
	}
	
	@PostMapping("/addTrip")
	public ResponseEntity<Trip> saveTrip(@RequestParam("header_id") Integer customerId, @RequestBody Trip trip) {
		return new ResponseEntity<Trip>(tripService.saveTrip(customerId, trip), HttpStatus.CREATED);
	}

	@GetMapping("/trip/list")
	public List<Trip> getTripsByCustomerId(@RequestParam("header_id") Integer customerId) {
		return tripService.getTripsByCustomerId(customerId);
	} 
	
	@PostMapping("/trip/update")
	public JsonResponseBase updateTrip(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("header_id"));
		Integer tripId = Integer.parseInt(request.getParameter("id"));
		
		Trip retrievedTrip = this.tripService.findById(tripId);
		
		if(retrievedTrip.getCustomer().getId() != customerId) {
			throw new RuntimeException("Object don't belong to customer!");
		}
		
		retrievedTrip.setFromPostal(request.getParameter("fromPostal"));
		retrievedTrip.setToPostal(request.getParameter("toPostal"));
		retrievedTrip.setDays(request.getParameter("days"));
		retrievedTrip.setDescription(request.getParameter("description"));
		retrievedTrip.setRole(Integer.parseInt(request.getParameter("role")));
		retrievedTrip.setTimeOfDay(Integer.parseInt(request.getParameter("timeOfDay"))); 
		
		this.tripService.save(retrievedTrip); 
		JsonResponseBase rsp = new JsonResponseBase();
		rsp.header_rsp = "ok";
		
		return rsp;
	}
	
	@PostMapping("/trip/delete")
	public JsonResponseBase deleteTrip(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("header_id"));
		Integer tripId = Integer.parseInt(request.getParameter("id"));
		
		Trip retrievedTrip = this.tripService.findById(tripId);
		
		if(retrievedTrip.getCustomer().getId() != customerId) {
			throw new RuntimeException("Object don't belong to customer!");
		}
		
		this.tripService.deleteTrip(retrievedTrip);
		JsonResponseBase rsp = new JsonResponseBase();
		rsp.header_rsp = "ok";
		return rsp;
	} 
}