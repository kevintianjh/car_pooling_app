package com.accenture.carpooling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import com.accenture.carpooling.entity.DiscussionMessage;
import com.accenture.carpooling.entity.DiscussionRoom;

public interface DiscussionMessageRepository extends JpaRepository<DiscussionMessage, Integer> {
	
	@Query("SELECT dm FROM DiscussionMessage dm WHERE dm.discussionRoom=?1")
	public Page<DiscussionMessage> listByDiscussionRoom(Pageable pageable, DiscussionRoom discussionRoom);
} 
