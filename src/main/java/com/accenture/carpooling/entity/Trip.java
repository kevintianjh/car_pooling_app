package com.accenture.carpooling.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trip")
public class Trip {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@Column(name="from_postal")
	private String fromPostal;
	
	@Column(name="to_postal")
	private String toPostal;
	
	@Column(name="role")
	private Integer role; //1 denotes Driver, 2 denotes Passenger
	
	@Column(name = "tags")
	private String tags;
	
	@Column(name = "description")
	private String description;
	


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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
}
