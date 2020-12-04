package com.springboot.bookshop.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.entity.Account;
import com.springboot.bookshop.entity.Review;



@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	Optional<Review> findByReviewId(String reviewId);
	List<Review> findAllByEmail(String email);
	List<Review> findAllByProductId(String productId);
}