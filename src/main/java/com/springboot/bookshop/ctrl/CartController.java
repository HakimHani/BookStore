package com.springboot.bookshop.ctrl;

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

import com.springboot.bookshop.Account;
import com.springboot.bookshop.Cart;
import com.springboot.bookshop.ItemInfo;
import com.springboot.bookshop.ShopItem;
import com.springboot.bookshop.User;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AccountRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.repo.UserRepository;




@RestController
@RequestMapping("/api/cart")
@Scope("session")
public class CartController {



	@Autowired
	private Visitor visitor;
	
	@Autowired
	private ItemInfoRepository itemInfoRepository;

	// get all users
	@GetMapping
	public Cart getCart() {
		return this.visitor.getCart();
	}

	// get user by id
	@GetMapping("/atc/{productId}")
	public String getUserByEmail(@PathVariable (value = "productId") String productId) {

		if(visitor == null) {
			visitor = new Visitor();
		}
		//ShopItem itema = new ShopItem("Apple","9.99","1234","XL",UUID.randomUUID().toString().replace("-", ""));
		ItemInfo itemInfo= this.itemInfoRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Item Info not found with pid :" + productId));
		if(itemInfo.getInventory() < 1) {
			throw new ResourceNotFoundException("ItemInfo out of stock :" + productId);
		}
		ShopItem itema = new ShopItem(itemInfo);	
		return visitor.addToCart(itema,itemInfo);
	}

	// create user
	@PostMapping("/add")
	public String addToCart(@RequestBody ShopItem item) {

		if(visitor == null) {
			visitor = new Visitor();
		}
		
		ItemInfo itemInfo= this.itemInfoRepository.findByProductId(item.getItemSku() + item.getSizeSku())
				.orElseThrow(() -> new ResourceNotFoundException("Item Info not found with productId :" + item.getItemSku() + item.getSizeSku()));
		if(itemInfo.getInventory() < 1) {
			return "Item is out of stock";
		}
		
		ShopItem itema = new ShopItem(itemInfo);
		return visitor.addToCart(itema,itemInfo);
	}
	
	
	// create user
	@PostMapping("/remove")
	public String removeFromCart(@RequestBody ShopItem item) {

		if(visitor == null) {
			visitor = new Visitor();
		}
		
		return visitor.removeFromCart(item);
	}
	


}
