package com.accenture.carpooling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "discussion_room")
public class DiscussionRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="from_postal")
	private String fromPostal;
	
	@Column(name="to_postal") 
	private String toPostal;
	
	public DiscussionRoom() {}
	
	public DiscussionRoom(int id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromPostal() {
		return fromPostal;
	}

	public void setFromPostal(String fromPostal) {
		this.fromPostal = fromPostal;
	}

	public String getToPostal() {
		return toPostal;
	}

	public void setToPostal(String toPostal) {
		this.toPostal = toPostal;
	} 

}
