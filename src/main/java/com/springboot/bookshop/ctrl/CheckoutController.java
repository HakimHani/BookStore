package com.springboot.bookshop.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.TreeMap;

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

import com.springboot.bookshop.Address;
import com.springboot.bookshop.Billing;
import com.springboot.bookshop.Checkout;
import com.springboot.bookshop.IdentificationGenerator;
import com.springboot.bookshop.ShopItem;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.enums.CheckoutState;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.CheckoutRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;

@RestController
@Scope("session")
@RequestMapping("/api/checkout")
public class CheckoutController {



	@Autowired
	private Visitor visitor;

	@Autowired
	private CheckoutRepository checkoutRepo;

	@Autowired
	private IdentificationGenerator idGenerator;

	@Autowired
	private ItemInfoRepository itemInfoRepository;

	//Fetch all checkouts of the user by email
	@GetMapping("/{email}")
	public List<Checkout> getCheckoutsByEmail(@PathVariable (value = "email") String email) {
		return  this.checkoutRepo.findAllByEmail(email);
	}


	//Fetch checkout by checkout id
	@GetMapping("/checkoutId/{checkoutId}")
	public Optional<Checkout> getCheckoutByCheckoutId(@PathVariable (value = "checkoutId") String checkoutId) {
		Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		return  this.checkoutRepo.findByCheckoutId(checkoutId);
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
			Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
			System.out.println(parseStringToList(existingCheckout.getItems()).toString() + " | " + ids.toString());
			if(existingCheckout != null && parseStringToList(existingCheckout.getItems()).equals(ids)){
				return "recovered previous checkout";
			}
		}


		checkout.setCheckoutState(CheckoutState.DEFAULT);
		String checkoutId = idGenerator.generateCheckoutId();
		checkout.setCheckoutId(checkoutId);
		checkout.setPaymentGatewayId(idGenerator.generatePaymentGatewayId());

		checkout.setItems(ids.toString());
		checkout.setTotal(total);
		this.checkoutRepo.save(checkout);
		this.visitor.getCart().setCheckoutId(checkoutId);
		return "New checkout created";
	}


	// update shipping
	@PutMapping("/shipping/{checkoutId}")
	public Checkout updateShipping(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		existingCheckout.setCheckoutState(CheckoutState.SHIPPING_INFO);
		existingCheckout.setEmail(checkout.getEmail());
		existingCheckout.setAddressId(checkout.getAddressId());

		return this.checkoutRepo.save(existingCheckout);
	}


	// update billing
	@PutMapping("/billing/{checkoutId}")
	public Checkout updateBilling(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		existingCheckout.setCheckoutState(CheckoutState.BILLING_INFO);
		existingCheckout.setBillingId(checkout.getBillingId());

		return this.checkoutRepo.save(existingCheckout);
	}


	// processing billing
	@PutMapping("/processing/{checkoutId}")
	public Checkout updateFinal(@RequestBody Checkout checkout,@PathVariable ("checkoutId") String checkoutId) {

		Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		
		
		existingCheckout.setCheckoutState(CheckoutState.PROCESSING_BILLING);
		this.checkoutRepo.save(existingCheckout);
		return existingCheckout;
		//existingCheckout.setPaymentGatewayId(checkout.getPaymentGatewayId());

		/*
		ModelAndView modelAndView = new ModelAndView("redirect:" + "/api/checkout/payment_response");
		TreeMap<String, String> parameters = new TreeMap<>();
		parameters.put("purchase_Id", existingCheckout.getCheckoutId());
		parameters.put("transaction_amount", transactionAmount);
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
