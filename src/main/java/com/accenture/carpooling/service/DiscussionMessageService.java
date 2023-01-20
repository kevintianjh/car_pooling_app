package com.accenture.carpooling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component; 
import com.accenture.carpooling.entity.DiscussionMessage;
import com.accenture.carpooling.entity.DiscussionRoom;
import com.accenture.carpooling.repository.DiscussionMessageRepository;
/* Author: Kevin
 * Purpose: Business logic for creating and reading DiscussionMessage
 */
@Component
public class DiscussionMessageService {
	@Autowired private DiscussionMessageRepository discussionMessageRepository;
	
	public Page<DiscussionMessage> listByDiscussionRoom(int pageNo, int discussionRoomId) {
		Pageable pageable = Pageable.ofSize(10).withPage(pageNo);
		return this.discussionMessageRepository.listByDiscussionRoom(pageable, new DiscussionRoom(discussionRoomId));
	}
	
	public DiscussionMessage save(DiscussionMessage discussionMessage) {
		return this.discussionMessageRepository.save(discussionMessage);
	}
}
