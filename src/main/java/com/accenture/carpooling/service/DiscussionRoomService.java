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
	
	public boolean roomExists(String fromPostal, String toPostal) {
		String partialFromPostal = fromPostal.substring(0, 3);
		String partialtoPostal = toPostal.substring(0, 3);
		return this.discussionRoomRepository.roomExists(partialFromPostal, partialtoPostal);
	}
	
	public void save(DiscussionRoom dr) {
		this.discussionRoomRepository.save(dr);
	}
}
