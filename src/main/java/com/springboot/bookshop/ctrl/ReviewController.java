package com.springboot.bookshop.ctrl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.Address;
import com.springboot.bookshop.IdentificationGenerator;
import com.springboot.bookshop.Review;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.ReviewRepository;
import com.springboot.bookshop.repo.UserRepository;

@RestController
@RequestMapping("/api/review")
@Scope("session")
public class ReviewController {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private Visitor visitor;

	@Autowired
	private IdentificationGenerator idGenerator;
	
	// create user
	@PostMapping("/create")
	public Review createAddress(@RequestBody Review review) {

		review.setReviewId(idGenerator.generateAddressId());
		review.setReviewDate(new Date());
		this.reviewRepository.save(review);
		return review;
	}
	
	

	@GetMapping("/email/{email}")
	public List<Review> getReviewByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.reviewRepository.findAllByEmail(cutomerEmail);
	}

	@GetMapping("/productId/{productId}")
	public List<Review> getReviewByProductId(@PathVariable (value = "productId") String productId) {
		return  this.reviewRepository.findAllByProductId(productId);
	}

	@GetMapping("/id/{reviewId}")
	public Review getReviewByReviewId(@PathVariable (value = "reviewId") String reviewId) {
		Review existingReview = this.reviewRepository.findByReviewId(reviewId).orElse(null);
		if(existingReview == null) {
			System.out.println("Review not found with reId :" + reviewId);
			throw new ResourceNotFoundException("Review not found with reviewId :" + reviewId);
		}
		return  existingReview;
	}
	
	
	@GetMapping("/rating/{productId}")
	public double getRatingByProductId(@PathVariable (value = "productId") String productId) {
		double rate = 0;
		List<Review> reviews = this.reviewRepository.findAllByProductId(productId);
		
		if(reviews.size() == 0) {
			return 0.00;
		}
		
		for(Review review : reviews) {
			rate += review.getRate();
		}
		
		
		return rate/(double) reviews.size();
	}



}
