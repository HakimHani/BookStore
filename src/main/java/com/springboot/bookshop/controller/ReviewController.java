package com.springboot.bookshop.controller;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.Review;
import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.service.ReviewService;
import com.springboot.bookshop.service.SalesService;
import com.springboot.bookshop.utils.IdentificationGenerator;
import com.springboot.bookshop.utils.ResponseBuilder;

@RestController
@RequestMapping("/api/review")
@Scope("session")
public class ReviewController {


	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private ItemInfoService itemInfoService;

	@Autowired
	private Visitor visitor;

	@Autowired
	private IdentificationGenerator idGenerator;
	
	@Autowired
	private ResponseBuilder responseBuilder;
	
	// create review and comments
	@PostMapping("/create")
	public ResponseEntity<Object> createReview(@RequestBody Review review) {

		if(this.visitor.getUser() == null) {
			return new ResponseEntity<Object>(responseBuilder.reviewResponse("Failed","INVALID USER", review), HttpStatus.OK);
		}
		
		List<Sales> eSales = this.salesService.findAllByCustomerEmail(this.visitor.getUser().getEmail());
		if(eSales.size() == 0) {
			return new ResponseEntity<Object>(responseBuilder.reviewResponse("Failed","NO PURCHASED HISTORY FOUND", review), HttpStatus.OK);
		}
		
		Boolean isPurchased = false;
		for(Sales cSales : eSales) {
			if(cSales.getItemId().equals(review.getproductId())) {
				isPurchased = true;
				break;
			}
		}
		
		if(!isPurchased) {
			return new ResponseEntity<Object>(responseBuilder.reviewResponse("Failed","USER HAVE NOT PURCHASED THIS ITEM", review), HttpStatus.OK);
		}
		
		
		ItemInfo targetItem = this.itemInfoService.findByProductId(review.getproductId()).orElse(null);
		if(targetItem == null) {
			return new ResponseEntity<Object>(responseBuilder.reviewResponse("Failed","ITEM NOT FOUND", review), HttpStatus.OK);
		}
		

		
		
		review.setEmail(this.visitor.getUser().getEmail());
		review.setLastName(this.visitor.getUser().getLastName());
		review.setReviewId(idGenerator.generateAddressId());
		review.setDate("placeholder");
		review.setReviewDate(new Date());
		this.reviewService.save(review);
		
		List<Review> reviews = this.reviewService.findAllByProductId(review.getproductId());
		if(reviews.size() == 0) {
			return new ResponseEntity<Object>(responseBuilder.reviewResponse("Failed","ERROR SAVING REVIEW", review), HttpStatus.OK);
		}

		double total = 0;
		for(Review cReview : reviews) {
			total += cReview.getRate();
		}
		double fRatings = total / reviews.size();
		targetItem.setRate(fRatings);
		this.itemInfoService.save(targetItem);
		return new ResponseEntity<Object>(responseBuilder.reviewResponse("Success","Successfully saved review and comment", review), HttpStatus.OK);
	}
	
	

	@GetMapping("/email/{email}")
	public List<Review> getReviewByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.reviewService.findAllByEmail(cutomerEmail);
	}

	@GetMapping("/productId/{productId}")
	public List<Review> getReviewByProductId(@PathVariable (value = "productId") String productId) {
		return  this.reviewService.findAllByProductId(productId);
	}

	@GetMapping("/id/{reviewId}")
	public Review getReviewByReviewId(@PathVariable (value = "reviewId") String reviewId) {
		Review existingReview = this.reviewService.findByReviewId(reviewId).orElse(null);
		if(existingReview == null) {
			System.out.println("Review not found with reId :" + reviewId);
			throw new ResourceNotFoundException("Review not found with reviewId :" + reviewId);
		}
		return  existingReview;
	}
	
	
	@GetMapping("/rating/{productId}")
	public double getRatingByProductId(@PathVariable (value = "productId") String productId) {
		double rate = 0;
		List<Review> reviews = this.reviewService.findAllByProductId(productId);
		
		if(reviews.size() == 0) {
			return 0.00;
		}
		
		for(Review review : reviews) {
			rate += review.getRate();
		}
		
		
		return rate/(double) reviews.size();
	}



}
