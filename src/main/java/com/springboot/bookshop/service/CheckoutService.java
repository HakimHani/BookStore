package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.repo.CheckoutRepository;


@Service
public class CheckoutService {
	private final CheckoutRepository checkoutRepository;


	public CheckoutService(CheckoutRepository checkoutRepository) {
		this.checkoutRepository = checkoutRepository;
	}


	public List<Checkout> findAll(){
		return this.checkoutRepository.findAll();
	}

	public Optional<Checkout> findById(Long id){
		return this.checkoutRepository.findById(id);
	}
	
	public Optional<Checkout> findByCheckoutId(String checkoutId){
		return this.checkoutRepository.findByCheckoutId(checkoutId);
	}

	public List<Checkout> findAllByEmail(String email){
		return this.checkoutRepository.findAllByEmail(email);
	}
	
	public List<Checkout> findAllByEmailAndCheckoutState(String email,CheckoutState checkoutState){
		return this.checkoutRepository.findAllByEmailAndCheckoutState(email,checkoutState);
	}

	public Checkout save(Checkout checkout){
		return this.checkoutRepository.save(checkout);
	}
	
	public void delete(Checkout checkout){
		this.checkoutRepository.delete(checkout);
	}


}
