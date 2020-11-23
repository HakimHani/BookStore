package com.springboot.bookshop;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdentificationGenerator {


	IdentificationGenerator() {

	}
	
	public String generateAddressId() {
		return UUID.randomUUID().toString();
	}
	
	public String generateBillingId() {
		return UUID.randomUUID().toString();
	}

	public String generateCheckoutId() {
		return UUID.randomUUID().toString();
	}
	
	public String generatePaymentGatewayId() {
		return UUID.randomUUID().toString();
	}


}
