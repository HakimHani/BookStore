package com.springboot.bookshop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	private long id;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;

	@Column(name="first_name", nullable = false)
	private String firstName;

	@Column(name="last_name", nullable = false)
	private String lastName;
	
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Column(name="default_shipping_address_id")
	private String defaultShippingAddr;
	

	@Column(name="default_billing_address_id")
	private String defaultBillingAddr;




	public User() {

	}

	public User(String email, String firstName,String lastName,String phone) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fistName) {
		this.firstName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getDefaultShippingAddr() {
		return defaultShippingAddr;
	}

	public void setDefaultShippingAddr(String defaultShippingAddr) {
		this.defaultShippingAddr = defaultShippingAddr;
	}

	public String getDefaultBillingAddr() {
		return defaultBillingAddr;
	}

	public void setDefaultBillingAddr(String defaultBillingAddr) {
		this.defaultBillingAddr = defaultBillingAddr;
	}









}
