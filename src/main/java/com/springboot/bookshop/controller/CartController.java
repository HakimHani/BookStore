package com.springboot.bookshop.controller;



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
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Cart;
import com.springboot.bookshop.model.ShopItem;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.utils.ResponseBuilder;




@RestController
@RequestMapping("/api/cart")
@Scope("session")
public class CartController {



	@Autowired
	private Visitor visitor;
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@Autowired
	private ResponseBuilder responseBuilder;

	// get all users
	@GetMapping
	public Cart getCart() {
		return this.visitor.getCart();
	}

	// ADD ITEM TO CART
	@GetMapping("/atc/{productId}")
	public ResponseEntity<Object> addItemToCart(@PathVariable (value = "productId") String productId) {

		ItemInfo itemInfo= this.itemInfoService.findByProductId(productId).orElse(null);
		if(itemInfo == null) {
			 return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ITEM NOT FOUND", null), HttpStatus.OK);
		}
		if(itemInfo.getInventory() < 1) {
			return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ITEM OUT OF STOCK", itemInfo), HttpStatus.OK);
		}
		ShopItem itema = new ShopItem(itemInfo);
		String atcResult = visitor.addToCart(itema,itemInfo);
		if(!atcResult.equals("Successfully added " + itemInfo.getItemLabel())) {
			return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ERROR ADD TO CART", itemInfo), HttpStatus.OK);
		};
		return new ResponseEntity<Object>(responseBuilder.cartResponse("Success","ITEM ADDED TO CART", itemInfo), HttpStatus.OK);
	}

	// ADD TO CART
	@PostMapping("/add")
	public ResponseEntity<Object> addToCart(@RequestBody ShopItem item) {

		ItemInfo itemInfo= this.itemInfoService.findByProductId(item.getItemSku() + item.getSizeSku()).orElse(null);
		if(itemInfo == null) {
			 return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ITEM NOT FOUND", item), HttpStatus.OK);
		}
		if(itemInfo.getInventory() < 1) {
			return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ITEM OUT OF STOCK", item), HttpStatus.OK);
		}
		ShopItem itema = new ShopItem(itemInfo);
		String atcResult = visitor.addToCart(itema,itemInfo);
		if(!atcResult.equals("Successfully added " + itemInfo.getItemLabel())) {
			return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ERROR ADD TO CART", item), HttpStatus.OK);
		};
		return new ResponseEntity<Object>(responseBuilder.cartResponse("Success","ITEM ADDED TO CART", item), HttpStatus.OK);
	}
	
	
	// create user
	@PostMapping("/rm")
	public ResponseEntity<Object> removeFromCart(@RequestBody ShopItem item) {
		if(!visitor.rmFromCart(item)) {
			return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ERROR REMOVING ITEM", item), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(responseBuilder.cartResponse("Success","ITEM REMOVED FROM CART", item), HttpStatus.OK);
	}
	
	@GetMapping("/remove/{itemSessionId}")
	public ResponseEntity<Object> removeFromCart(@PathVariable String itemSessionId) {
		if(!visitor.removeFromCart(itemSessionId)) {
			return new ResponseEntity<Object>(responseBuilder.cartResponse("Failed","ERROR REMOVING ITEM", itemSessionId), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(responseBuilder.cartResponse("Success","ITEM REMOVED FROM CART", itemSessionId), HttpStatus.OK);
	}
	


}
