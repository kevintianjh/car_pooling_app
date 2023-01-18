package com.accenture.carpooling.entity;

import java.util.Date; 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discussion_message")
public class DiscussionMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	  
	@ManyToOne
	@JoinColumn(name = "discussion_room_id")
	private DiscussionRoom discussionRoom;  
	
	@Column(name="message")
	private String message;
	
	@Column(name="fileMessage")
	private String fileMessage;
	
	@ManyToOne
	@JoinColumn(name="customer_id") 
	private Customer customer;
	
	@Column(name = "date")
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DiscussionRoom getDiscussionRoom() {
		return discussionRoom;
	}

	public void setDiscussionRoom(DiscussionRoom discussionRoom) {
		this.discussionRoom = discussionRoom;
	}

	public String getFileMessage() {
		return fileMessage;
	}

	public void setFileMessage(String fileMessage) {
		this.fileMessage = fileMessage;
	}   
}
