package com.springboot.bookshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.ShopItem;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.CheckoutService;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.service.SalesService;
import com.springboot.bookshop.utils.IdentificationGenerator;
import com.springboot.bookshop.utils.ResponseBuilder;

@RestController
@Scope("session")
@RequestMapping("/api/checkout")
public class CheckoutController {



	@Autowired
	private Visitor visitor;

	@Autowired
	private CheckoutService checkoutService;

	@Autowired
	private IdentificationGenerator idGenerator;

	@Autowired
	private ItemInfoService itemInfoService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private ResponseBuilder responseBuilder;

	
	
	//Fetch all checkouts of the user by email
	@GetMapping("/{email}")
	public List<Checkout> getCheckoutsByEmail(@PathVariable (value = "email") String email) {
		return  this.checkoutService.findAllByEmail(email);
	}
	
	


	//Fetch checkout by checkout id
	@GetMapping("/checkoutId/{checkoutId}")
	public Optional<Checkout> getCheckoutByCheckoutId(@PathVariable (value = "checkoutId") String checkoutId) {
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		return  this.checkoutService.findByCheckoutId(checkoutId);
	}
	
	


	//create checkout
	@PostMapping("/create")
	public String createCheckout(@RequestBody Checkout checkout) {

		ArrayList<ShopItem> inCartItems = (ArrayList<ShopItem>) visitor.getCart().getItems();
		if(inCartItems.size() == 0) {
			return "Cart is empty";
		}
		double total = 0;
		ArrayList<String> ids = new ArrayList<String>();
		for (ShopItem item : inCartItems) {
			double price = item.getItemPrice();
			String itemId = item.getItemSku() + item.getSizeSku();
			total += price;
			ids.add(itemId);
			// may need actually validating stock and price code (from itemInfo table)
			System.out.println("Checking price for " + itemId + " | price " + Double.toString(price));
		}



		if(visitor.getCart().getCheckoutId() != null) {
			System.out.println("Detected previous checkout");
			Checkout existingCheckout = this.checkoutService.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
			System.out.println(parseStringToList(existingCheckout.getItems()).toString() + " | " + ids.toString());
			if(existingCheckout != null && parseStringToList(existingCheckout.getItems()).equals(ids)){
				return "recovered previous checkout";
			}
		}

		if(this.visitor.getUser() != null) {
			checkout.setEmail(this.visitor.getUser().getEmail());
		}

		checkout.setCheckoutState(CheckoutState.DEFAULT);
		String checkoutId = idGenerator.generateCheckoutId();
		checkout.setCheckoutId(checkoutId);
		checkout.setPaymentGatewayId(idGenerator.generatePaymentGatewayId());

		checkout.setItems(ids.toString());
		checkout.setTotal(total);
		this.checkoutService.save(checkout);
		this.visitor.getCart().setCheckoutId(checkoutId);
		return "New checkout created";
	}
	
	
	


	// update shipping
	@PutMapping("/shipping/{checkoutId}")
	public ResponseEntity<Object> updateShipping(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Failed", "CHECKOUT EXPIRED", null), HttpStatus.OK);
			//throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		
		existingCheckout.setCheckoutState(CheckoutState.SHIPPING_INFO);
		//existingCheckout.setEmail(checkout.getEmail());
		existingCheckout.setEmail(this.visitor.getUser().getEmail());
		existingCheckout.setAddressId(checkout.getAddressId());
		this.checkoutService.save(existingCheckout);
		
		return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Success", "Successfully update checkout shipping", existingCheckout), HttpStatus.OK);
	}
	
	


	// update billing
	@PutMapping("/billing/{checkoutId}")
	public ResponseEntity<Object> updateBilling(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Failed", "CHECKOUT EXPIRED", null), HttpStatus.OK);
			//throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		existingCheckout.setCheckoutState(CheckoutState.BILLING_INFO);
		existingCheckout.setBillingId(checkout.getBillingId());
		this.checkoutService.save(existingCheckout);
		return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Success", "Successfully update checkout billing", existingCheckout), HttpStatus.OK);
	}

	
	
	

	// processing billing
	@PutMapping("/processing/{checkoutId}")
	public ResponseEntity<Object> updateFinal(@RequestBody Checkout checkout,@PathVariable ("checkoutId") String checkoutId) {
		System.out.println("PROCESSING CHECKOUT RESULT.........................................");
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Failed", "CHECKOUT EXPIRED", null), HttpStatus.OK);
			//throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		
		System.out.println("VALIDATING CART ITEMS FOR " + checkoutId);
		
		List<ItemInfo> dbItems = new ArrayList<ItemInfo>();
		for(ShopItem inCartItem : this.visitor.getCart().getItems()) {
			String itemId = inCartItem.getItemSku() + inCartItem.getSizeSku();
			ItemInfo dbItem = this.itemInfoService.findByProductId(itemId).orElse(null);
			if(dbItem == null) {
				return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Failed", "CARTITEM NOT FOUND " + itemId, null), HttpStatus.OK);
			}
			if(dbItem.getInventory() <= 0.0) {
				return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Failed", "CARTITEM OUT OF STOCK DURING CHECKOUT " + itemId, null), HttpStatus.OK);
			}
			/*
			if(dbItem.isAvaliable() == false) {
				return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Failed", "CARTITEM DISABLED " + itemId, null), HttpStatus.OK);
			}*/
			//NEED EXTRA CHECK IF THERE ARE MULTIPLE SAME ITEM IN CART
			dbItems.add(dbItem);
		}
		
		for(ItemInfo confirmedItem : dbItems) {
			String pid = confirmedItem.getProductId();
			
			confirmedItem.setSalesCount((confirmedItem.getSalesCount() + 1));
			confirmedItem.setInventory((confirmedItem.getInventory() - 1));
			this.itemInfoService.save(confirmedItem);
			System.out.println("UPDATE COUNT AND INVENTORY FOR " + pid);
					
			Sales nSales = new Sales(idGenerator.generateCheckoutId(),pid,checkoutId,existingCheckout.getEmail());
			nSales.setDate(new Date());
			salesService.save(nSales);
			System.out.println("ADDED SALES TABLE FOR " + pid);	
		}
		
		
		existingCheckout.setCheckoutState(CheckoutState.PROCESSING_BILLING);
		this.checkoutService.save(existingCheckout);
		return new ResponseEntity<Object>(responseBuilder.CheckoutResponse("Success", "Successfully processed checkout", existingCheckout), HttpStatus.OK);
	}
	
	
	
	


	private List<String> parseStringToList(String column) {
		List<String> output = new ArrayList<>();
		String listString = column.substring(1, column.length() - 1);
		StringTokenizer stringTokenizer = new StringTokenizer(listString,",");
		while (stringTokenizer.hasMoreTokens()){
			output.add(stringTokenizer.nextToken());
		}
		return output;
	}
}
