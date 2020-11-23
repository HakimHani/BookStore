package com.springboot.bookshop.enums;

public enum CreditCardType {
	INVALID,VISA,MASTERCARD,DISCOVER;

	public String getCreditCardType() {

		// this will refer to the object SMALL
		switch(this) {
		case INVALID:
			return "invalid";

		case VISA:
			return "visa";

		case MASTERCARD:
			return "mastercard";

		case DISCOVER:
			return "discover";

		default:
			return null;
		}
	}
}
