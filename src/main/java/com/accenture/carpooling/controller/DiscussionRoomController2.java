package com.accenture.carpooling.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller; 
import org.springframework.web.socket.messaging.SessionDisconnectEvent; 
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.service.CustomerService; 

@Controller
public class DiscussionRoomController2 { 
	@Autowired SimpMessagingTemplate simpMessagingTemplate;
	@Autowired CustomerService customerService;
	
	public static class DiscussionRoomJson {
		public String dr_id;
	}
	
	public static class UsersListJson {
		public Collection<String> list;
	}
	
	private HashMap<Integer, HashMap<Integer, String>> usersOnlineMap = new HashMap<>(); 
	
	public void broadcastRoomNewMessage(int discussionRoomId) {
		this.simpMessagingTemplate.convertAndSend("/client/new-message-status/"+discussionRoomId, "");
	} 
	
	@MessageMapping("/check-in")
	public void joinRoom(Principal principal, DiscussionRoomJson drJson) {
		Integer drIdInt = Integer.parseInt(drJson.dr_id);
		Integer customerId = Integer.parseInt(principal.getName());
		
		Customer customer = this.customerService.findById(customerId);
		
		//Remove this customer from all list to keep list clean
		removeUser(customerId);
		
		if(!this.usersOnlineMap.containsKey(drIdInt)) {
			this.usersOnlineMap.put(drIdInt, new HashMap<>());
		}
		 
		this.usersOnlineMap.get(drIdInt).put(customerId, customer.getUsername());  
		
		//Broadcast to all subscribers that someone joined
		this.simpMessagingTemplate.convertAndSend("/client/users-online/" + drIdInt, this.usersOnlineMap.get(drIdInt).values());
	}
	
	public void removeUser(Integer customerId) {
		this.usersOnlineMap.keySet().forEach(t -> {
			
			if(this.usersOnlineMap.get(t).containsKey(customerId)) {
				
				this.usersOnlineMap.get(t).remove(customerId);
				
				//Broadcast to all subscribers that someone left 
				this.simpMessagingTemplate.convertAndSend("/client/users-online/" + t, this.usersOnlineMap.get(t).values());
				return;
			}
		});
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) throws Exception {  
		Principal principal = event.getUser(); 
		Integer customerId = Integer.parseInt(principal.getName());
		
		//Final check to be sure customer is not inside "users online" list
		removeUser(customerId);
	} 
}
