package com.springboot.bookshop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataValidation {
	
	
	public boolean validateBilling(Billing billing) {
		
		boolean flag = true;
		String creditCard = billing.getCardNumber();
		String ccv = billing.getCcv();
		String expirationDate = billing.getExpDate();
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		creditCard = creditCard.replaceAll("-", "");
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(creditCard);
	    flag = matcher.matches();
	    
	    flag = (ccv.length() == 3 && ccv.matches("3[0-9]")) ? true : false;
	    
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
	    simpleDateFormat.setLenient(false);
	    Date expiry;
		try {
			expiry = simpleDateFormat.parse(expirationDate);
			flag = !(expiry.before(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return flag;   
	}
	
	public boolean validateAddress(Address address) {
		boolean flag = true;
		// Todo
		
		return flag;
	}
	
}
