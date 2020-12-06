package com.springboot.bookshop.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.springboot.bookshop.constant.enums.AccountType;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.User;

import java.sql.Timestamp;

@Component
@Scope("session")
public class Visitor {
	
	
	Timestamp firstTS;
	Timestamp latestTS;
	User user;
	Cart cart;
	AccountType permission;
	int errorRate;
	
	public Visitor() {
		this.firstTS = new Timestamp(System.currentTimeMillis());
		this.latestTS = this.firstTS;
		this.user = null;
		this.cart = new Cart();
		this.permission = AccountType.VISITOR;
		this.errorRate = 0;
	}
	
	public Visitor(Timestamp firstTS) {
		this.firstTS = firstTS;
		this.latestTS = this.firstTS;
		this.user = null;
		this.cart = new Cart();
		this.permission = AccountType.VISITOR;
		this.errorRate = 0;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Timestamp getFirstTS() {
		return firstTS;
	}

	public void setFirstTS(Timestamp firstTS) {
		this.firstTS = firstTS;
		this.latestTS = firstTS;
	}

	public Timestamp getLatestTS() {
		return latestTS;
	}

	public void setLatestTS(Timestamp latestTS) {
		this.latestTS = latestTS;
	}
	
	public void refresh() {
		this.latestTS = new Timestamp(System.currentTimeMillis());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void logIn(User user) {
		this.user = user;
		//this.errorRate = 0;
	}
	
	public void logOut(User user) {
		this.user = null;
		permission = AccountType.VISITOR;
		this.errorRate = 0;
	}
	
	public String addToCart(ShopItem item,ItemInfo itemInfo) {

		return this.cart.addItem(item, itemInfo);
	}
	
	public boolean rmFromCart(ShopItem item) {
		if(this.cart.rmItem(item)) {
			return true;
		}
		return false;
	}
	
	
	public boolean removeFromCart(String itemSessionId) {
		if(this.cart.removeItem(itemSessionId)) {
			return true;
		}
		return false;
	}

	public AccountType getPermission() {
		return permission;
	}

	public void setPermission(AccountType permission) {
		this.permission = permission;
	}
	

	public int getErrorRate() {
		return errorRate;
	}

	public void setErrorRate(int errorRate) {
		this.errorRate = errorRate;
	}
	
	public boolean errorEvent() {
		this.errorRate += 1;
		if(this.errorRate >= 3) {
			this.cart.setCheckoutId(null);
			this.errorRate = 0;
			return true;
		}
		return false;
	}
	
	

}
