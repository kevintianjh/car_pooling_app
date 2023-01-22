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

import com.accenture.carpooling.config.MyHandshakeHandler.MyPrincipal;
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.service.CustomerService; 

/* Author: Kevin Tian
 * Purpose: Additional business logic for DiscussionRoom
 */

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
	
	public static class Typing {
		public String username;
		public String dr_id;
	}
	
	//Map to show all users online categorize by Discussion Room ID
	private HashMap<Integer, HashMap<Integer, String>> usersOnlineMap = new HashMap<>(); 
	
	//Used by DiscussionRoomController to notify all users inside the room to refresh 
	//for new message
	public void broadcastRoomNewMessage(int discussionRoomId) {
		this.simpMessagingTemplate.convertAndSend("/client/new-message-status/"+discussionRoomId, "");
	} 
	
	//Send by user on typing event
	@MessageMapping("/typing")
	public void typing(Typing typing) { 
		this.simpMessagingTemplate.convertAndSend("/client/typing/" + typing.dr_id, typing);
	}
	
	//Send by user when stops typing
	@MessageMapping("/stop-typing")
	public void stopTyping(Typing typing) { 
		this.simpMessagingTemplate.convertAndSend("/client/stop-typing/" + typing.dr_id, typing);
	}
	
	//Check-in
	@MessageMapping("/check-in")
	public void joinRoom(Principal principal, DiscussionRoomJson drJson) {
		Integer drIdInt = Integer.parseInt(drJson.dr_id);
		MyPrincipal myPrincipal = (MyPrincipal)principal;  
		
		//Remove this customer from all list to keep list clean
		removeUser(myPrincipal.getId());
		
		if(!this.usersOnlineMap.containsKey(drIdInt)) {
			this.usersOnlineMap.put(drIdInt, new HashMap<>());
		}
		 
		this.usersOnlineMap.get(drIdInt).put(myPrincipal.getId(), myPrincipal.getUsername());  
		
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
		MyPrincipal principal = (MyPrincipal)event.getUser();   
		
		//Final check to be sure customer is not inside "users online" list
		removeUser(principal.getId());
	} 
}
