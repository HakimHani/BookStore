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
import com.springboot.bookshop.ItemInfo;
import com.springboot.bookshop.Review;
import com.springboot.bookshop.Sales;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.repo.ReviewRepository;
import com.springboot.bookshop.repo.SalesRepository;
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
	private SalesRepository salesRepository;
	
	@Autowired
	private ItemInfoRepository itemRepo;

	@Autowired
	private Visitor visitor;

	@Autowired
	private IdentificationGenerator idGenerator;
	
	// create user
	@PostMapping("/create")
	public String createAddress(@RequestBody Review review) {

		if(this.visitor.getUser() == null) {
			return "Failed, invalid user";
		}
		
		List<Sales> eSales = this.salesRepository.findAllByCustomerEmail(this.visitor.getUser().getEmail());
		if(eSales.size() == 0) {
			return "Failed, no purchase history found for this user";
		}
		
		Boolean isPurchased = false;
		for(Sales cSales : eSales) {
			if(cSales.getItemId().equals(review.getproductId())) {
				isPurchased = true;
				break;
			}
		}
		
		if(!isPurchased) {
			return "Failed, user haven not purchased this item";
		}
		
		
		ItemInfo targetItem = this.itemRepo.findByProductId(review.getproductId()).orElse(null);
		if(targetItem == null) {
			return "Failed, error getting product detail";
		}
		

		
		
		review.setEmail(this.visitor.getUser().getEmail());
		review.setLastName(this.visitor.getUser().getLastName());
		review.setReviewId(idGenerator.generateAddressId());
		review.setDate("placeholder");
		review.setReviewDate(new Date());
		this.reviewRepository.save(review);
		
		List<Review> reviews = this.reviewRepository.findAllByProductId(review.getproductId());
		if(reviews.size() == 0) {
			return "Failed saving review";
		}

		double total = 0;
		for(Review cReview : reviews) {
			total += cReview.getRate();
		}
		double fRatings = total / reviews.size();
		targetItem.setRate(fRatings);
		this.itemRepo.save(targetItem);
		return "Successfully saved comment and rating";
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
