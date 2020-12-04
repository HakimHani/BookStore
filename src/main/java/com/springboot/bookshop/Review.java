package com.springboot.bookshop;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	private long id;
	
	@Column(name="review_id", nullable = false, unique = true)
	private String reviewId;
	
	@Column(name="email", nullable = false, unique = false)
	private String email;

	@Column(name="date", nullable = false)
	private String date;

	@Column(name="last_name", nullable = false)
	private String lastName;
	
	@Column(name="rate", precision= 1, scale= 3,nullable = false)
	private double rate;

	@Column(name="comment", nullable = false)
	private String comment;
	
	@Column(name="product_id", nullable = false)
	private String productId;
	
	@Column(name="reviewDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date reviewDate;




	public Review() {

	}

	public Review(String email, String date,String lastName,double rate,String comment,String productId) {
		super();
		this.reviewId = UUID.randomUUID().toString();
		this.email = email;
		this.date = date;
		this.lastName = lastName;
		this.rate = rate;
		this.comment = comment;
		this.productId = productId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getproductId() {
		return productId;
	}

	public void setproductId(String productId) {
		this.productId = productId;
	}


	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}


	



}
