package main.java.com.springboot.bookshop.enums;

public enum AccountType {
	VISITOR,CUSTOMER,PARTNER,ADMIN,OWNER;

	public String getAccountType() {

		// this will refer to the object SMALL
		switch(this) {
		case VISITOR:
			return "visitor";

		case CUSTOMER:
			return "customer";

		case PARTNER:
			return "partner";

		case ADMIN:
			return "admin";
			
		case OWNER:
			return "owner";

		default:
			return null;
		}
	}
}
