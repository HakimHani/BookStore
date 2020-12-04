package com.springboot.bookshop.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="address")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	@JsonIgnore
	private long id;
	
	@Column(name="address_id", nullable = false, unique = true)
	private String addressId;
	
	@Column(name="email", nullable = false, unique = false)
	private String email;

	@Column(name="first_name", nullable = false)
	private String firstName;

	@Column(name="last_name", nullable = false)
	private String lastName;
	
	@Column(name="address_one", nullable = false)
	private String addressOne;

	@Column(name="address_two", nullable = false)
	private String addressTwo;
	
	@Column(name="city", nullable = false)
	private String city;

	@Column(name="country", nullable = false)
	private String country;

	@Column(name="state", nullable = false)
	private String state;

	@Column(name="postal", nullable = false)
	private String postal;
	
	@Column(name="phone", nullable = false)
	private String phone;




	public Address() {

	}

	public Address(String email, String firstName,String lastName,String addressOne,String addressTwo,String city,String country,String state,String postal,String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.city = city;
		this.country = country;
		this.state = state;
		this.postal = postal;
		this.phone = phone;
		this.addressId = UUID.randomUUID().toString();
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

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}












}
