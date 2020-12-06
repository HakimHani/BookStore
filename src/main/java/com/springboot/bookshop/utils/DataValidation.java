package com.springboot.bookshop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.Billing;

@Component
public class DataValidation {
	
	
	DataValidation(){
		
	}
	
	public boolean validateBilling(Billing billing) {
		
		boolean flag = true;
		String creditCard = billing.getCardNumber();
		String ccv = billing.getCcv();
		String year = billing.getExpDate();
		String month = billing.getExpMonth();
		
		flag = Pattern.compile("^[0-9]{4}$").matcher(year).matches() && Pattern.compile("^[0-9]{2}$").matcher(month).matches();
		if(!flag) {
			return false;
		}
		
		int exp_year = Integer.parseInt(year);
		int exp_month = Integer.parseInt(month);
		
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		creditCard = creditCard.replaceAll("-", "");
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(creditCard);
	    flag = flag && matcher.matches();
    
	    flag = flag && Pattern.compile("^[0-9]{3}$").matcher(ccv).matches();
	    flag = flag && isValidDate(exp_month, exp_year);
	    return flag;   
	}
	
	public boolean validateAddress(Address address) {
			
		boolean flag = true;
		String fname = address.getFirstName();
		String lname = address.getLastName();
		String em = address.getEmail();
		String city = address.getCity();
		String state = address.getState();
		String postalCode = address.getPostal();
		String country = address.getCountry();
		String addressOne = address.getAddressOne();
		flag = flag && !(addressOne == null || fname.chars().allMatch(Character::isWhitespace));
		flag = flag && !(lname == null || lname.chars().allMatch(Character::isWhitespace));
		flag = flag && !(em == null || em.chars().allMatch(Character::isWhitespace));
		flag = flag && !(addressOne == null || addressOne.chars().allMatch(Character::isWhitespace));
		if(!flag)
			return flag;
		flag = flag && city.matches( "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" ); 
		flag = flag &&  state.matches("Québec|Ontario|British Columbia|Manitoba|Saskatchewan|Newfoundland|Nova Scotia|Alberta|Prince Edward Island|New Brunswick");
		flag = flag && Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$").matcher(postalCode).matches();
		flag = flag && country.matches("Canada") || country.matches("CA");	  		
		return flag;
	}
	
	private  boolean isValidDate(int month, int year)
    {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH); 
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year >= currentYear)
        {
            if (year > currentYear)
            {
                return true;
            }

            if (year == currentYear)
            {
                if (month >= currentMonth)
                {
                    return true;
                }
            }       
        }            

        return false;
    }
	
}
