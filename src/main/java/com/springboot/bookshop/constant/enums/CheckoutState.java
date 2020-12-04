package com.springboot.bookshop.constant.enums;

public enum CheckoutState {
	DEFAULT,SHIPPING_INFO,SHIPPING_METHOD,BILLING_INFO,COMPLETED,PROCESSING_BILLING;

	public String getCheckoutState() {

		// this will refer to the object SMALL
		switch(this) {
		case DEFAULT:
			return "default";

		case SHIPPING_INFO:
			return "shipping_info";

		case SHIPPING_METHOD:
			return "shipping_method";

		case BILLING_INFO:
			return "billing_info";
			
		case COMPLETED:
			return "completed";
			
		case PROCESSING_BILLING:
			return "processing_billing";
			
		default:
			return null;
		}
	}
}
