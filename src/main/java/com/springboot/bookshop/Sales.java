package com.springboot.bookshop;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name="sales")
public class Sales implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	@JsonIgnore
	private long id;

	@Column(name="sales_id", nullable = false, unique = true)
	private String salesId;

	@Column(name="item_id", nullable = false)
	private String itemId;

	@Column(name="checkout_id", nullable = false)
	private String checkoutId;
	
	@Column(name="cutomer_email", nullable = false)
	private String customerEmail;

	@Column(name="date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;




	public Sales() {

	}

	public Sales(String salesId,String itemId,String checkoutId,String customerEmail) {
		super();
		this.salesId = salesId;
		this.itemId = itemId;
		this.checkoutId = checkoutId;
		this.customerEmail = customerEmail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}






}
