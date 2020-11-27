package com.springboot.bookshop;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONArray;

import com.springboot.bookshop.enums.CheckoutState;
import com.springboot.bookshop.enums.CreditCardType;


@Entity
@Table(name="checkout")
public class Checkout implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	private long id;

	@Column(name="creation_date", nullable = false)
	private String creationDate;

	@Column(name="checkout_id", nullable = false, unique=true)
	private String checkoutId;

	@Column(name="auth_token", nullable = false)
	private String authToken;

	@Column(name="update_log", nullable = false)
	private String updateLog;

	@Column(name="checkout_state", nullable = false)
	private CheckoutState checkoutState;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="address_id", nullable = false)
	private String addressId;

	@Column(name="billing_id", nullable = false)
	private String billingId;

	@Column(name="payment_gateway_id", nullable = false, unique=true)
	private String paymentGatewayId;
	
	@Column(name="items", nullable = false)
	private String items;
	
	@Column(name="total", nullable = false)
	private double total;





	public Checkout() {

	}

	public Checkout(String creationDate, String checkoutId, String authToken,String updateLog,CheckoutState checkoutState,String email,String addressId,String billingId,String paymentGatewayId,String items, double total) {
		super();
		this.creationDate = creationDate;
		this.checkoutId = checkoutId;
		this.authToken = authToken;
		this.updateLog = updateLog;
		this.checkoutState = checkoutState;
		this.email = email;
		this.addressId = addressId;
		this.billingId = billingId;
		this.paymentGatewayId = paymentGatewayId;
		this.items = items;
		this.total = total;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public CheckoutState getCheckoutState() {
		return checkoutState;
	}

	public void setCheckoutState(CheckoutState checkoutState) {
		this.checkoutState = checkoutState;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}

	public String getPaymentGatewayId() {
		return paymentGatewayId;
	}

	public void setPaymentGatewayId(String paymentGatewayId) {
		this.paymentGatewayId = paymentGatewayId;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
















}
