package com.accenture.carpooling.controller;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.accenture.carpooling.entity.DiscussionRoom;
import com.accenture.carpooling.entity.DiscussionMessage;
import com.accenture.carpooling.service.DiscussionMessageService;
import com.accenture.carpooling.service.DiscussionRoomService;

@RestController
@CrossOrigin
@RequestMapping("/discussion-room")
public class DiscussionRoomController {
	
	@Autowired private DiscussionRoomService discussionRoomService;
	@Autowired private DiscussionMessageService discussionMessageService; 
	
	@GetMapping("")
	public List<DiscussionRoom> listByCustomerTrip() {
		return this.discussionRoomService.listByCustomerTrip(1);
	}
	
	@GetMapping("/messages")
	public List<DiscussionMessage> listDmByRoom() {
		return discussionMessageService.listByDiscussionRoom(0, 1).toList();
	}
}
