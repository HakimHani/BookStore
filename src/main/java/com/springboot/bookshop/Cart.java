package com.springboot.bookshop;

import java.util.ArrayList;
import java.util.List;

public class Cart {

	private List<ShopItem> items;
	private String checkoutId;


	Cart(){
		this.items = new ArrayList<ShopItem>();
	}

	public List<ShopItem> getItems() {
		return items;
	}

	public void setItems(List<ShopItem> items) {
		this.items = items;
	}

	public String addItem(ShopItem item,ItemInfo itemInfo) {
		int duplicatCount = 0;
		for(int i=0; i < this.items.size(); i++) {
			if(this.items.get(i).getItemSku().equals(item.getItemSku())) {
				duplicatCount += 1;
			}
		}
		if(duplicatCount >= itemInfo.getMaxCartNumber()) {
			return "You can only have " + itemInfo.getMaxCartNumber() + " of " + itemInfo.getItemLabel() +" in cart";
		}
		this.items.add(item);
		return "Successfully added " + itemInfo.getItemLabel();
	}

	public boolean rmItem(ShopItem item) {
		for(int i=0; i < this.items.size(); i++) {
			if(this.items.get(i).getItemSessionId().equals(item.getItemSessionId())) {
				this.items.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeItem(String itemSessionId) {
		for(int i=0; i < this.items.size(); i++) {
			if(this.items.get(i).getItemSessionId().equals(itemSessionId)) {
				this.items.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
	

	public boolean clearCart() {
		this.items.clear();
		return true;
	}

	public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
	}

	public double getTotal() {
		double total = 0;
		for (ShopItem item : getItems()) {
			double price = item.getItemPrice();
			total += price;
		}
		return total;
	}

	public ArrayList<String> getIds() {
		ArrayList<String> ids = new ArrayList<String>();
		for (ShopItem item : getItems()) {
			String itemId = item.getItemSku() + item.getSizeSku();
			ids.add(itemId);
		}
		return ids;
	}


}
