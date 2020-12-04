package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.repo.BillingRepository;


@Service
public class BillingService {
	private final BillingRepository billingRepository;


	public BillingService(BillingRepository billingRepository) {
		this.billingRepository = billingRepository;
	}


	public List<Billing> findAll(){
		return this.billingRepository.findAll();
	}

	public Optional<Billing> findById(Long id){
		return this.billingRepository.findById(id);
	}
	
	public Optional<Billing> findByBillingId(String billingId){
		return this.billingRepository.findByBillingId(billingId);
	}

	public List<Billing> findAllByEmail(String email){
		return this.billingRepository.findAllByEmail(email);
	}

	public Billing save(Billing billing){
		return this.billingRepository.save(billing);
	}
	
	public void delete(Billing billing){
		this.billingRepository.delete(billing);
	}


}
