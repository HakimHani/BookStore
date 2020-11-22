package com.springboot.bookshop.ctrl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.springboot.bookshop.enums.CheckoutState;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.CheckoutRepository;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

	
	@Autowired
	private CheckoutRepository checkoutRepo;
	
	
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
		checkout.setCheckoutState(CheckoutState.DEFAULT);
		this.checkoutRepo.save(checkout);
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
	public Checkout updateFinal(@RequestBody Checkout checkout, @PathVariable ("checkoutId") String checkoutId) {
	
		Checkout existingCheckout = this.checkoutRepo.findByCheckoutId(checkoutId).orElse(null);
		if(existingCheckout == null) {
			System.out.println("Checkout not found with checkoutId :" + checkoutId);
			throw new ResourceNotFoundException("Checkout not found with checkoutId :" + checkoutId);
		}
		existingCheckout.setCheckoutState(CheckoutState.PROCESSING_BILLING);
		//existingCheckout.setPaymentGatewayId(checkout.getPaymentGatewayId());

		return this.checkoutRepo.save(existingCheckout);
	}
	
	
}
