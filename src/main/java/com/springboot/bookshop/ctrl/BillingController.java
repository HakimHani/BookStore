package com.springboot.bookshop.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.Address;
import com.springboot.bookshop.Billing;
import com.springboot.bookshop.Checkout;
import com.springboot.bookshop.DataValidation;
import com.springboot.bookshop.IdentificationGenerator;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.BillingRepository;
import com.springboot.bookshop.repo.CheckoutRepository;

@RestController
@Scope("session")
@RequestMapping("/api/billing")
public class BillingController {
	@Autowired
	private BillingRepository billingRepo;
	
	@Autowired
	private IdentificationGenerator idGenerator;
	
	@Autowired
	private Visitor visitor;

	@Autowired
	private CheckoutRepository checkoutRepo;
	
	@GetMapping("/email/{email}")
	public List<Billing> getBillingsByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.billingRepo.findAllByEmail(cutomerEmail);
	}
	
	
	@PostMapping("/create")
	public Billing createBilling(@RequestBody Billing billing) {
		
		if(this.visitor.getCart().getCheckoutId() == null) {
			throw new ResourceNotFoundException("Invalid checkout");
		}
		Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(visitor.getCart().getCheckoutId()).orElse(null);
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
		this.billingRepo.save(billing);
		//return "New billing created";
		return billing;
	}
	
	
	
	
}
