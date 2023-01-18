package com.accenture.carpooling.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile; 
import com.accenture.carpooling.entity.DiscussionRoom;
import com.accenture.carpooling.json.JsonResponseBase;
import com.accenture.carpooling.entity.Customer;
import com.accenture.carpooling.entity.DiscussionMessage;
import com.accenture.carpooling.service.CustomerService;
import com.accenture.carpooling.service.DiscussionMessageService;
import com.accenture.carpooling.service.DiscussionRoomService; 
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class DiscussionRoomController {
	
	@Autowired private DiscussionRoomService discussionRoomService;
	@Autowired private DiscussionMessageService discussionMessageService; 
	@Autowired private CustomerService customerService;
	@Autowired private ServletContext servletContext;
	@Autowired private DiscussionRoomController2 drc2;
	
	public static class JsonResponse extends JsonResponseBase {
		public List<DiscussionRoom> discussion_room_list;
		public Customer customer;
		public int total_message_pages;
		public List<DiscussionMessage> message_list;
		public DiscussionMessage added_message;
	}
	
	@GetMapping("/discussion-room/list")
	public JsonResponse m1(HttpServletRequest request) { 
		int customerId = Integer.parseInt(request.getParameter("header_id")); 
		
		List<DiscussionRoom> drList = this.discussionRoomService.listByCustomerTrip(customerId);
		JsonResponse jsRsp = new JsonResponse();
		
		if(drList != null && drList.size() != 0) {
			Page<DiscussionMessage> page = discussionMessageService.listByDiscussionRoom(0, drList.get(0).getId());
			jsRsp.message_list = page.getContent();
			jsRsp.total_message_pages = page.getTotalPages();
		}
		
		jsRsp.discussion_room_list = drList;
		jsRsp.customer = this.customerService.findById(customerId);
		return jsRsp;
	}
	
	@GetMapping("/discussion-message/list")
	public JsonResponse m2(HttpServletRequest request) {
		int page = Integer.parseInt(request.getParameter("page"));
		int discussionRoomId = Integer.parseInt(request.getParameter("dr_id"));
		JsonResponse jsRsp = new JsonResponse(); 
		
		if(page <= 0) {
			return jsRsp;
		} 
		
		Page<DiscussionMessage> retPage = discussionMessageService.listByDiscussionRoom(page-1, discussionRoomId);
		jsRsp.message_list = retPage.getContent();
		jsRsp.total_message_pages = retPage.getTotalPages(); 
		return jsRsp;
	}
	
	@GetMapping("/discussion-message/add")
	public JsonResponse m3(HttpServletRequest request) {
		String message = request.getParameter("message");
		int customerId = Integer.parseInt(request.getParameter("header_id")); 
		int discussionRoomId = Integer.parseInt(request.getParameter("dr_id"));
		
		Customer customer = this.customerService.findById(customerId); 
		DiscussionMessage msg = new DiscussionMessage();
		msg.setCustomer(customer);
		msg.setDiscussionRoom(new DiscussionRoom(discussionRoomId));
		msg.setDate(new Date());
		msg.setMessage(message);
		msg.setFileMessage("");
		
		DiscussionMessage addedMsg = this.discussionMessageService.save(msg);  
		
		JsonResponse jsRsp = new JsonResponse();
		jsRsp.header_rsp = "ok";
		jsRsp.added_message = addedMsg;
		
		this.drc2.broadcastRoomNewMessage(discussionRoomId); 
		
		return jsRsp;
	}
	
	@PostMapping("/discussion-message/add-file")
	public JsonResponse m4(@RequestParam("file") MultipartFile file,
						   @RequestParam("header_id") int customerId,
						   @RequestParam("dr_id") int discussionRoomId) throws IOException {
		
		String fileName = customerId + "_" + this.generateRandomFileName() + "_" + file.getOriginalFilename();
		
		String uploadDirPath = servletContext.getRealPath("/") + "\\discussion-message-uploads";
		File uploadDir = new File(uploadDirPath);
		if(!uploadDir.exists()) {
			if(!uploadDir.mkdir()) {
				throw new RuntimeException("Cannot create directory 'discussion-message-uploads'");
			}
		}
		
		String finalFilePath = uploadDirPath + "\\" + fileName;
		File finalFile = new File(finalFilePath);
		file.transferTo(finalFile); 
		
		Customer customer = this.customerService.findById(customerId); 
		DiscussionMessage msg = new DiscussionMessage();
		msg.setCustomer(customer);
		msg.setDiscussionRoom(new DiscussionRoom(discussionRoomId));
		msg.setDate(new Date());
		msg.setFileMessage(fileName);
		msg.setMessage("");
		
		DiscussionMessage addedMsg = this.discussionMessageService.save(msg);
		JsonResponse jsRsp = new JsonResponse();
		jsRsp.header_rsp = "ok";
		jsRsp.added_message = addedMsg;
		
		this.drc2.broadcastRoomNewMessage(discussionRoomId); 
		
		return jsRsp;
	}
	
	public String generateRandomFileName() { 
		StringBuilder secret = new StringBuilder(); 
		Random rand = new Random();
		
		for(int count = 0; count < 20; count++) { 
			int choice = rand.nextInt(1, 4);
			
			switch(choice) {
				case 1:
					secret.append((char)rand.nextInt(97, 123));
					break;
				case 2: 
					secret.append((char)rand.nextInt(65, 91)); 
					break;
				case 3:
					secret.append((char)rand.nextInt(48, 58)); 
					break; 
			}
		}
		
		return secret.toString();
	}
}