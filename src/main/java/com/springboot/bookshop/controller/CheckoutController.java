package com.springboot.bookshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.ShopItem;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.CheckoutRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.repo.SalesRepository;
import com.springboot.bookshop.service.CheckoutService;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.service.SalesService;
import com.springboot.bookshop.utils.IdentificationGenerator;

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
	public Checkout updateShipping(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		existingCheckout.setCheckoutState(CheckoutState.SHIPPING_INFO);
		//existingCheckout.setEmail(checkout.getEmail());
		existingCheckout.setEmail(this.visitor.getUser().getEmail());
		existingCheckout.setAddressId(checkout.getAddressId());

		return this.checkoutService.save(existingCheckout);
	}


	// update billing
	@PutMapping("/billing/{checkoutId}")
	public Checkout updateBilling(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		existingCheckout.setCheckoutState(CheckoutState.BILLING_INFO);
		existingCheckout.setBillingId(checkout.getBillingId());

		return this.checkoutService.save(existingCheckout);
	}


	// processing billing
	@PutMapping("/processing/{checkoutId}")
	public Checkout updateFinal(@RequestBody Checkout checkout,@PathVariable ("checkoutId") String checkoutId) {
		System.out.println("PROCESSING CHECKOUT RESULT.........................................");
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		
		System.out.println("ADDING SALES COUNT.........................................");
		
		for(ShopItem inCartItem : this.visitor.getCart().getItems()) {
			String itemId = inCartItem.getItemSku() + inCartItem.getSizeSku();
			System.out.println("Adding sales count for " + itemId);
			
			ItemInfo dbItem = this.itemInfoService.findByProductId(itemId)
					.orElseThrow(() -> new ResourceNotFoundException("Item not found with sku :" + itemId));
			dbItem.setSalesCount((dbItem.getSalesCount() + 1));
			//ALSO need inventory deduction code
			this.itemInfoService.save(dbItem);
			System.out.println("ADDED COUNT.........................................");
			
			Sales nSales = new Sales(idGenerator.generateCheckoutId(),itemId,checkoutId,existingCheckout.getEmail());
			nSales.setDate(new Date());
			salesService.save(nSales);
			System.out.println("ADDED SALES.........................................");
			
			
		}
		
		System.out.println("RETURN PROCESSING RESULT.........................................");
		
		existingCheckout.setCheckoutState(CheckoutState.PROCESSING_BILLING);
		this.checkoutService.save(existingCheckout);
		return existingCheckout;
		//existingCheckout.setPaymentGatewayId(checkout.getPaymentGatewayId());

		/*
		ModelAndView modelAndView = new ModelAndView("redirect:" + "/api/checkout/payment_response");
		TreeMap<String, String> parameters = new TreeMap<>();
		parameters.put("purchase_Id", existingCheckout.getCheckoutId());
		parameters.put("transaction_amount", existingCheckout.getTotal()+ "");
		parameters.put("customer_Id", existingCheckout.getEmail());
		modelAndView.addAllObjects(parameters);
		return modelAndView;*/
		

	}

	// send the response back to the client to be displayed
	@PostMapping(value = "/payment_response")
	public String getResponseRedirect(HttpServletRequest request, Model model) {
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		System.out.println("RESULT : "+parameters.toString());
		model.addAttribute("parameters",parameters);
		return "report";
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
