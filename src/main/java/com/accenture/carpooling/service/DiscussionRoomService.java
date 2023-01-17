package com.accenture.carpooling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 
import com.accenture.carpooling.entity.DiscussionRoom;
import com.accenture.carpooling.repository.DiscussionRoomRepository;

@Component
public class DiscussionRoomService {
	@Autowired private DiscussionRoomRepository discussionRoomRepository;
	
	public List<DiscussionRoom> listByCustomerTrip(int customerId) {
		return this.discussionRoomRepository.listByCustomerTrip(customerId);
	}
}
