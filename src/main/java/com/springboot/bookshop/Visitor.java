package com.springboot.bookshop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
@Scope("session")
public class Visitor {
	
	
	Timestamp firstTS;
	Timestamp latestTS;
	User user;
	Cart cart;
	
	public Visitor() {
		this.firstTS = new Timestamp(System.currentTimeMillis());
		this.latestTS = this.firstTS;
		this.user = null;
		this.cart = new Cart();
	}
	
	public Visitor(Timestamp firstTS) {
		this.firstTS = firstTS;
		this.latestTS = this.firstTS;
		this.user = null;
		this.cart = new Cart();
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
	}
	
	public void logOut(User user) {
		this.user = null;
	}
	
	public String addToCart(ShopItem item,ItemInfo itemInfo) {

		return this.cart.addItem(item, itemInfo);
	}
	
	public String rmFromCart(ShopItem item) {
		if(this.cart.rmItem(item)) {
			return "Successfully removed item to cart";
		}
		return "Failed removing item to cart";
	}
	
	
	public String removeFromCart(String itemSessionId) {
		if(this.cart.removeItem(itemSessionId)) {
			return "Successfully removed item to cart";
		}
		return "Failed removing item to cart";
	}

}
