package com.springboot.bookshop.controller;

import java.util.List;

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


import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.BillingService;
import com.springboot.bookshop.service.CheckoutService;
import com.springboot.bookshop.utils.DataValidation;
import com.springboot.bookshop.utils.IdentificationGenerator;
import com.springboot.bookshop.utils.ResponseBuilder;

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
	
	@Autowired
	private DataValidation dataValidation;
	
	@Autowired
	private ResponseBuilder responseBuilder;
	
	@GetMapping("/email/{email}")
	public List<Billing> getBillingsByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.billingService.findAllByEmail(cutomerEmail);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<Object> createBilling(@RequestBody Billing billing) {
		
		
		if(this.visitor.getCart().getCheckoutId() == null) {
			//throw new ResourceNotFoundException("Invalid checkout");
			return new ResponseEntity<Object>(responseBuilder.BillingResponse("Failed", "CHECKOUT EXPIRED", billing), HttpStatus.OK);
		}
		Checkout existingCheckout = this.checkoutService.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
		if(existingCheckout == null) {
			return new ResponseEntity<Object>(responseBuilder.BillingResponse("Failed", "INVALID CHECKOUT", billing), HttpStatus.OK);
			//throw new ResourceNotFoundException("Invalid checkout");
		}

		if(!dataValidation.validateBilling(billing)) {
			if(this.visitor.errorEvent()) {
				return new ResponseEntity<Object>(responseBuilder.BillingResponse("Failed", "CHECKOUT BANNED", billing), HttpStatus.OK);
			}
			return new ResponseEntity<Object>(responseBuilder.BillingResponse("Failed", "PAYMENT VALIDATION FAILED", billing), HttpStatus.OK);
		}
		
		String email = existingCheckout.getEmail();
		billing.setEmail(email);
		billing.setBillingId(idGenerator.generateBillingId());
		this.billingService.save(billing);
		//return "New billing created";
		return new ResponseEntity<Object>(responseBuilder.BillingResponse("Success", "Successfully created billing", billing), HttpStatus.OK);
	}
	
	
	
	
}
