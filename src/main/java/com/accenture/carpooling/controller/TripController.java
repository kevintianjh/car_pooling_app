package com.accenture.carpooling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.DiscussionRoom;
import com.accenture.carpooling.entity.Trip;
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.service.DiscussionRoomService;
import com.accenture.carpooling.service.TripService; 
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class TripController { 
	
	@Autowired
	private TripService tripService; 
	@Autowired
	private DiscussionRoomService discussionRoomService;
	
	@PostMapping("/trip/add") 
	public JsonResponseBase saveTrip(HttpServletRequest request) {  
		Integer customerId = Integer.parseInt(request.getParameter("header_id"));
		
		Trip newTrip = new Trip();
		newTrip.setCustomer(new Customer(customerId));
		newTrip.setFromPostal(request.getParameter("fromPostal"));
		newTrip.setToPostal(request.getParameter("toPostal"));
		newTrip.setDays(request.getParameter("days"));
		newTrip.setDescription(request.getParameter("description"));
		newTrip.setRole(Integer.parseInt(request.getParameter("role")));
		newTrip.setTimeOfDay(Integer.parseInt(request.getParameter("timeOfDay"))); 
		this.tripService.save(newTrip);  
		 
		if(!this.discussionRoomService.roomExists(newTrip.getFromPostal(), newTrip.getToPostal())) {
			DiscussionRoom dr = new DiscussionRoom();
			dr.setId(null);
			dr.setFromPostal(newTrip.getFromPostal().substring(0, 3));
			dr.setToPostal(newTrip.getToPostal().substring(0, 3)); 
			this.discussionRoomService.save(dr);
		} 
		
		JsonResponseBase jsRsp = new JsonResponseBase();
		jsRsp.header_rsp = "ok";
	
		return jsRsp;
	}
 
	@GetMapping("/trip/list")
	public List<Trip> getTripsByCustomerId(@RequestParam("header_id") Integer customerId) {
		return tripService.getTripsByCustomerId(customerId);
	} 
	 
	@GetMapping("/trip/getTripSameDest/{fromPost}/{toPost}")
	public List<Trip> getTripsWithSameDestination(@PathVariable("fromPost") String fromPostal, @PathVariable("toPost") String toPostal) {
		return tripService.getTripsWithSameDestination(fromPostal, toPostal);
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