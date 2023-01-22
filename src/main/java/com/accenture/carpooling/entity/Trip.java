package com.accenture.carpooling.entity; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "trip")
public class Trip { 
	public Trip() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	@NotNull
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@Column(name="from_postal")
	@Pattern(regexp = "^[0-9]{6}$")
	private String fromPostal;
	
	@Column(name="to_postal")
	@Pattern(regexp = "^[0-9]{6}$")
	private String toPostal;
	
	@Column(name="role")
	@Min(value = 1)
	@Max(value = 2) 
	private Integer role; //1 denotes Driver, 2 denotes Passenger
	
	@Column(name="days")
	@Pattern(regexp = "^[1-7]{1,7}$")
	public String days; //String value with each char of 1-7 e.g "125" represent Monday, Tuesday and Friday
	
	@Column(name="timeOfDay")
	@Pattern(regexp = "^[123]{1}$")
	public String timeOfDay; //String value: 1 denotes morning, 2 denotes afternoon, 3 denotes evening
	 
	@Column(name = "description")
	@NotBlank
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
 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", customer=" + customer + ", fromPostal=" + fromPostal + ", toPostal=" + toPostal
				+ ", role=" + role + ", days=" + days + ", timeOfDay=" + timeOfDay + ", description=" + description
				+ "]";
	}      
}
