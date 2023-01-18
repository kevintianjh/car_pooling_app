package com.accenture.carpooling.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import com.accenture.carpooling.entity.DiscussionRoom;

public interface DiscussionRoomRepository extends JpaRepository<DiscussionRoom, Integer> {
	
	@Query("SELECT dr FROM DiscussionRoom dr JOIN Trip t WHERE SUBSTRING(t.fromPostal, 1, 3)=dr.fromPostal AND SUBSTRING(t.toPostal, 1, 3)=dr.toPostal AND t.customer.id=?1")
	public List<DiscussionRoom> listByCustomerTrip(int customerId); 
} 