package com.springboot.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.BillingService;
import com.springboot.bookshop.service.CheckoutService;
import com.springboot.bookshop.utils.DataValidation;
import com.springboot.bookshop.utils.IdentificationGenerator;

@RestController
@Scope("session")
@RequestMapping("/api/billing")
public class BillingController {
	@Autowired
	private BillingService billingService;
	
	@Autowired
	private IdentificationGenerator idGenerator;
	
	@Autowired
	private Visitor visitor;

	@Autowired
	private CheckoutService checkoutService;
	
	@GetMapping("/email/{email}")
	public List<Billing> getBillingsByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.billingService.findAllByEmail(cutomerEmail);
	}
	
	
	@PostMapping("/create")
	public Billing createBilling(@RequestBody Billing billing) {
		
		if(this.visitor.getCart().getCheckoutId() == null) {
			throw new ResourceNotFoundException("Invalid checkout");
		}
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
		if(existingCheckout == null) {
			throw new ResourceNotFoundException("Invalid checkout");
		}
		DataValidation validate = new DataValidation();
		boolean validBilling = validate.validateBilling(billing);
		if(!validBilling) 
			return null;
		String email = existingCheckout.getEmail();
		billing.setEmail(email);
		billing.setBillingId(idGenerator.generateBillingId());
		this.billingService.save(billing);
		//return "New billing created";
		return billing;
	}
	
	
	
	
}
