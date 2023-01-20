package com.accenture.carpooling.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import com.accenture.carpooling.entity.DiscussionRoom;
/* Author: Kevin
 * Purpose: Additional selection of DiscussionRoom with specific search
 */
public interface DiscussionRoomRepository extends JpaRepository<DiscussionRoom, Integer> {
	
	@Query("SELECT dr FROM DiscussionRoom dr JOIN Trip t WHERE SUBSTRING(t.fromPostal, 1, 3)=dr.fromPostal AND SUBSTRING(t.toPostal, 1, 3)=dr.toPostal AND t.customer.id=?1")
	public List<DiscussionRoom> listByCustomerTrip(int customerId); 
	
	@Query("SELECT case when count(dr)> 0 then true else false end FROM DiscussionRoom dr WHERE dr.fromPostal=?1 AND dr.toPostal=?2")
	public boolean roomExists(String partialFromPostal, String partialToPostal);
} 