package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.entity.Review;
import com.springboot.bookshop.repo.BillingRepository;
import com.springboot.bookshop.repo.ReviewRepository;


@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;


	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}


	public List<Review> findAll(){
		return this.reviewRepository.findAll();
	}

	public Optional<Review> findById(Long id){
		return this.reviewRepository.findById(id);
	}
	
	public Optional<Review> findByReviewId(String reviewId){
		return this.reviewRepository.findByReviewId(reviewId);
	}
	
	public List<Review> findAllByProductId(String productId){
		return this.reviewRepository.findAllByProductId(productId);
	}

	public List<Review> findAllByEmail(String email){
		return this.reviewRepository.findAllByEmail(email);
	}

	public Review save(Review billing){
		return this.reviewRepository.save(billing);
	}
	
	public void delete(Review billing){
		this.reviewRepository.delete(billing);
	}


}
