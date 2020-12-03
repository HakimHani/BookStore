package com.springboot.bookshop;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.repo.ReviewRepository;

@SpringBootApplication
public class BookshopApplication {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ItemInfoRepository itemInfoRepository;

	HashMap<String,List<Double>> ratingsMap;

	public static void main(String[] args) {

		SpringApplication.run(BookshopApplication.class, args);
		System.out.println("Started ");
		//initRatings();
		//Testing
	}

	/*
	@EventListener(ApplicationReadyEvent.class)
	public void initRatings() {
		ratingsMap = new HashMap<String,List<Double>>();
		System.out.println("Init ratings");
		
		List<ItemInfo> items = this.itemInfoRepository.findAll();
		System.out.println("Got item repo size " + items.size());
		//List<Review> reviews = this.reviewRepository.findAllByProductId(productId);
		List<Review> reviews = this.reviewRepository.findAll();
		System.out.println("Got review repo size " + reviews.size());

		for(Review review : reviews) {
			System.out.println("Checking review " + review.getReviewId());
			//rate += review.getRate();
			String pid = review.getproductId();
			Double rate = review.getRate();
			if(ratingsMap.containsKey(pid)) {
				ratingsMap.get(pid).add(rate);
			}else {
				ratingsMap.put(pid, new ArrayList<Double>());
				ratingsMap.get(pid).add(rate);
			}
		}
		System.out.println("Map created..");

		for(ItemInfo item : items) {
			System.out.println("Checking item " + item.getProductId());
			String pid = item.getProductId();
			if(ratingsMap.containsKey(pid)) {
				double sum = 0;
				for(Double singleRatring : ratingsMap.get(pid)) {
					sum += singleRatring;
				}
				double fRating = sum/ratingsMap.get(pid).size();
				System.out.println("Ratings for " + pid + " is " + fRating);
				item.setRate(fRating);
			}else {
				item.setRate(0.00);
			}
			itemInfoRepository.save(item);
		}

		System.out.println("Rating init completed");


	}*/




}
