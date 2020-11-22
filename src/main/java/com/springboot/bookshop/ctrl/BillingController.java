package com.springboot.bookshop.ctrl;

import java.util.List;

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
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.BillingRepository;

@RestController
@RequestMapping("/api/billing")
public class BillingController {
	@Autowired
	private BillingRepository billingRepo;
	
	
	@GetMapping("/email/{email}")
	public List<Billing> getBillingsByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.billingRepo.findAllByEmail(cutomerEmail);
	}
	
	
	@PostMapping("/create")
	public String createBilling(@RequestBody Billing billing) {
		this.billingRepo.save(billing);
		return "New billing created";
	}
	
	
	
	
}
