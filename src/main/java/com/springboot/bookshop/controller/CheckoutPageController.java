package com.springboot.bookshop.controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.model.ShopItem;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.service.BillingService;
import com.springboot.bookshop.service.CheckoutService;
import com.springboot.bookshop.utils.IdentificationGenerator;



@Controller
@RequestMapping("/checkout")
@Scope("session")
public class CheckoutPageController {

	@Autowired
	private CheckoutService checkoutService;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private BillingService billingService;
	

	@Autowired
	private IdentificationGenerator idGenerator;



	@Autowired
	private Visitor visitor;


	@GetMapping()
	//@ResponseBody
	public String initialPage(Model model) {


		String validate = validateCheckout("initial");
		if(validate != "/checkout") {
			return "redirect:" + validate;
		}

		String email = "";
		if(this.visitor.getUser() != null) {
			email = visitor.getUser().getEmail();
		}


		Checkout checkout = new Checkout("placrholder", null, "auth","updatelog",null,email,"none","none","none","", 0);
		checkout.setCheckoutState(CheckoutState.DEFAULT);
		String checkoutId = idGenerator.generateCheckoutId();
		checkout.setCheckoutId(checkoutId);
		checkout.setPaymentGatewayId(idGenerator.generatePaymentGatewayId());
		checkout.setItems(this.visitor.getCart().getIds().toString());
		checkout.setTotal(this.visitor.getCart().getTotal());
		this.checkoutService.save(checkout);
		this.visitor.getCart().setCheckoutId(checkoutId);

		//return "New checkout created";
		return "redirect:/checkout/shipping?new";
	}

	@GetMapping("/shipping")
	//@ResponseBody
	public String shippingPage(Model model) {


		if(visitor.getCart().getCheckoutId() == null) {
			return "redirect:/checkout";
		}
		String validate = validateCheckout("shipping");
		if(!validate.contains("shipping")) {
			return "redirect:" + validate;
		}



		//List<Address> address = addressRepo.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("userInfo",this.visitor.getUser());
		//model.addAttribute("addressList",address);
		model.addAttribute("cart",this.visitor.getCart());

		model.addAttribute("checkoutId",visitor.getCart().getCheckoutId());
		if(this.visitor.getUser() == null) {
			model.addAttribute("isGuestCheckout",true);
			model.addAttribute("customerEmail","none");
		}else {
			model.addAttribute("isGuestCheckout",false);
			model.addAttribute("customerEmail",this.visitor.getUser().getEmail());
		}
		//return "checkout_shipping_page";
		return "shipping_page_sample";
	}


	@GetMapping("/billing")
	//@ResponseBody
	public String billing(Model model) {


		if(visitor.getCart().getCheckoutId() == null) {
			return "redirect:/checkout";
		}
		String validate = validateCheckout("billing");
		if(!validate.contains("billing")) {
			this.visitor.getCart().setCheckoutId(null);
			return "redirect:" + validate;
		}

		//Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);


		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("cart",this.visitor.getCart());
		model.addAttribute("checkoutId",visitor.getCart().getCheckoutId());
		if(this.visitor.getUser() == null) {
			model.addAttribute("isGuestCheckout",true);
			model.addAttribute("customerEmail","none");
		}else {
			model.addAttribute("isGuestCheckout",false);
			model.addAttribute("customerEmail",this.visitor.getUser().getEmail());
		}
		//return "checkout_shipping_page";
		return "billing";
	}
	
	
	@GetMapping("/review")
	//@ResponseBody
	public String review(Model model) {


		if(visitor.getCart().getCheckoutId() == null) {
			return "redirect:/checkout";
		}
		String validate = validateCheckout("review");
		if(!validate.contains("review")) {
			this.visitor.getCart().setCheckoutId(null);
			return "redirect:" + validate;
		}

		
		System.out.println("Getting checkout from db...........................");
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
		System.out.println("Getting checkout from db...........................");
		Address existingAddress = this.addressService.findByAddressId(existingCheckout.getAddressId()).orElse(null);
		System.out.println("Getting checkout from db...........................");
		Billing existingBilling = this.billingService.findByBillingId(existingCheckout.getBillingId()).orElse(null);
		if(existingCheckout == null || existingAddress == null || existingBilling == null) {
			return "redirect:/checkout/billing";
		}
		System.out.println("Retrive all info for review.............");
		
		model.addAttribute("checkoutAddress",existingAddress);
		model.addAttribute("checkoutBilling",existingBilling);
		model.addAttribute("checkoutDetail",existingCheckout);
		
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("cart",this.visitor.getCart());
		model.addAttribute("checkoutId",visitor.getCart().getCheckoutId());
		if(this.visitor.getUser() == null) {
			model.addAttribute("isGuestCheckout",true);
			model.addAttribute("customerEmail","none");
		}else {
			model.addAttribute("isGuestCheckout",false);
			model.addAttribute("customerEmail",this.visitor.getUser().getEmail());
		}
		//return "checkout_shipping_page";
		return "order-review";
	}
	
	
	
	@GetMapping("/success")
	//@ResponseBody
	public String orderResult(Model model) {


		if(visitor.getCart().getCheckoutId() == null) {
			return "redirect:/checkout";
		}
		String validate = validateCheckout("success");
		if(!validate.contains("success")) {
			this.visitor.getCart().setCheckoutId(null);
			return "redirect:" + validate;
		}

		
		System.out.println("Getting checkout from db...........................");
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
		System.out.println("Getting checkout from db...........................");
		Address existingAddress = this.addressService.findByAddressId(existingCheckout.getAddressId()).orElse(null);
		System.out.println("Getting checkout from db...........................");
		Billing existingBilling = this.billingService.findByBillingId(existingCheckout.getBillingId()).orElse(null);
		if(existingCheckout == null || existingAddress == null || existingBilling == null) {
			return "redirect:/index";
		}
		System.out.println("Retrive all info for review.............");
		
		model.addAttribute("checkoutAddress",existingAddress);
		model.addAttribute("checkoutBilling",existingBilling);
		model.addAttribute("checkoutDetail",existingCheckout);
		
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("cart",this.visitor.getCart());
		model.addAttribute("checkoutId",visitor.getCart().getCheckoutId());
		if(this.visitor.getUser() == null) {
			model.addAttribute("isGuestCheckout",true);
			model.addAttribute("customerEmail","none");
		}else {
			model.addAttribute("isGuestCheckout",false);
			model.addAttribute("customerEmail",this.visitor.getUser().getEmail());
		}
		
		
		this.visitor.getCart().clearCart();
		this.visitor.getCart().setCheckoutId(null);
		
		
		
		//return "checkout_shipping_page";
		return "order-result";
	}





	private String validateCheckout(String origin) {


		if(visitor == null) {
			System.out.println("checkout shipping page: new visitor");
			visitor = new Visitor();
			return "/index";
		}/*else if(visitor.getUser() == null) {
			System.out.println("checkout shipping page: no user");
			return "/index";
		}*/

		ArrayList<ShopItem> inCartItems = (ArrayList<ShopItem>) visitor.getCart().getItems();
		if(inCartItems.size() == 0) {
			return "/cart";
		}
		//double total = 0;
		ArrayList<String> ids = new ArrayList<String>();
		for (ShopItem item : inCartItems) {
			double price = item.getItemPrice();
			String itemId = item.getItemSku() + item.getSizeSku();
			//total += price;
			ids.add(itemId);
			System.out.println("Checking price for " + itemId + " | price " + Double.toString(price));
		}



		if(this.visitor.getCart().getCheckoutId() != null) {
			System.out.println("Detected previous checkout");
			Checkout existingCheckout = this.checkoutService.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
			//System.out.println(parseStringToList(existingCheckout.getItems()).toString() + " | " + ids.toString());
			Boolean isq = existingCheckout.getItems().toString().equals(ids.toString());
			//parseStringToList(existingCheckout.getItems()).equals(ids)
			if(existingCheckout != null && isq){	
				if(origin.equals("initial")) {
					if(existingCheckout.getCheckoutState() == CheckoutState.DEFAULT) {
						return "/checkout/shipping?recover";
					}else if(existingCheckout.getCheckoutState() == CheckoutState.SHIPPING_INFO) {
						return "/checkout/billing?recover";
					}else if(existingCheckout.getCheckoutState() == CheckoutState.BILLING_INFO) {
						return "/checkout/review?recover";
					}else if(existingCheckout.getCheckoutState() == CheckoutState.PROCESSING_BILLING) {
						return "/checkout/success?recover";
					}
				}else {
					return origin;
				}


			}else {
				this.visitor.getCart().setCheckoutId(null);
				return "/checkout";
			}
		}

		return "/checkout";

	}


}
