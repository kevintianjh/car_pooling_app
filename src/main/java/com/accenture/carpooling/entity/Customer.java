package com.accenture.carpooling.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;   
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GenerationType;

@Entity 
@Table(uniqueConstraints={@UniqueConstraint(columnNames = { "email"})}, name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	 
	@Column(name="username")
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email") 
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "dob")
	private Date dob;  

	public Customer() {
		super();
	} 
	
	public Customer(Integer id) {
		this.id = id;
	}
	
	public Customer(Integer id, 
					String username, 
					String name, 
					String email, 
					String phone, 
					Date dob) {
		super();
		this.id = id; 
		this.username = username;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.dob = dob; 
	} 
 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	} 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) { 
		this.phone = phone;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public void setDob(String dob) throws ParseException {
		this.dob = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", name=" + name + ", email=" + email + ", phone="
				+ phone + ", dob=" + dob + "]";
	}    
}
