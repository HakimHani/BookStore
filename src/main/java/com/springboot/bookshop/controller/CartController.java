package com.springboot.bookshop.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.entity.Account;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Cart;
import com.springboot.bookshop.model.ShopItem;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.repo.AccountRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.repo.UserRepository;
import com.springboot.bookshop.service.ItemInfoService;




@RestController
@RequestMapping("/api/cart")
@Scope("session")
public class CartController {



	@Autowired
	private Visitor visitor;
	
	@Autowired
	private ItemInfoService itemInfoService;

	// get all users
	@GetMapping
	public Cart getCart() {
		return this.visitor.getCart();
	}

	// get user by id
	@GetMapping("/atc/{productId}")
	public String getUserByEmail(@PathVariable (value = "productId") String productId) {


		//ShopItem itema = new ShopItem("Apple","9.99","1234","XL",UUID.randomUUID().toString().replace("-", ""));
		ItemInfo itemInfo= this.itemInfoService.findByProductId(productId).orElse(null);
				//.orElseThrow(() -> new ResourceNotFoundException("Item Info not found with pid :" + productId));
		if(itemInfo == null) {
			return "Failed, Item not found";
		}
		if(itemInfo.getInventory() < 1) {
			//throw new ResourceNotFoundException("ItemInfo out of stock :" + productId);
			return "Failed, Item out of stock";
		}
		ShopItem itema = new ShopItem(itemInfo);
		String atcResult = visitor.addToCart(itema,itemInfo);
		if(!atcResult.equals("Successfully added " + itemInfo.getItemLabel())) {
			return atcResult;
		};
		return "Success, item added to cart";
	}

	// create user
	@PostMapping("/add")
	public String addToCart(@RequestBody ShopItem item) {


		
		ItemInfo itemInfo= this.itemInfoService.findByProductId(item.getItemSku() + item.getSizeSku())
				.orElseThrow(() -> new ResourceNotFoundException("Item Info not found with productId :" + item.getItemSku() + item.getSizeSku()));
		if(itemInfo.getInventory() < 1) {
			return "Item is out of stock";
		}
		
		ShopItem itema = new ShopItem(itemInfo);
		return visitor.addToCart(itema,itemInfo);
	}
	
	
	// create user
	@PostMapping("/rm")
	public String removeFromCart(@RequestBody ShopItem item) {

	
		return visitor.rmFromCart(item);
	}
	
	@GetMapping("/remove/{itemSessionId}")
	public String removeFromCart(@PathVariable String itemSessionId) {

	
		return visitor.removeFromCart(itemSessionId);
	}
	


}
