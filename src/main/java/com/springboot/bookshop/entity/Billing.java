package com.springboot.bookshop.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.springboot.bookshop.constant.enums.CreditCardType;


@Entity
@Table(name="billing")
public class Billing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	private long id;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="card_type", nullable = false)
	private CreditCardType cardType;

	@Column(name="first_name", nullable = false)
	private String firstName;

	@Column(name="last_name", nullable = false)
	private String lastName;

	@Column(name="card_number", nullable = false)
	private String cardNumber;

	@Column(name="exp_month", nullable = false)
	private String expMonth;

	@Column(name="exp_date", nullable = false)
	private String expDate;

	@Column(name="ccv", nullable = false)
	private String ccv;

	@Column(name="postal", nullable = false)
	private String postal;
	
	@Column(name="billing_id", nullable = false, unique= true)
	private String billingId;




	public Billing() {

	}

	public Billing(String email, CreditCardType cardType, String firstName,String lastName,String cardNumber,String expMonth,String expDate,String ccv,String postal) {
		super();
		this.email = email;
		this.cardType = cardType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cardNumber = cardNumber;
		this.expMonth = expMonth;
		this.expDate = expDate;
		this.ccv = ccv;
		this.postal = postal;
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

	public CreditCardType getCardType() {
		return cardType;
	}

	public void setCardType(CreditCardType cardType) {
		this.cardType = cardType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getCcv() {
		return ccv;
	}

	public void setCcv(String ccv) {
		this.ccv = ccv;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}














}
