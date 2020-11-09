package com.springboot.bookshop;

import java.util.ArrayList;
import java.util.List;

public class Cart {

	private List<ShopItem> items;


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

	public boolean removeItem(ShopItem item) {
		for(int i=0; i < this.items.size(); i++) {
			if(this.items.get(i).getItemSessionId().equals(item.getItemSessionId())) {
				this.items.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean clearCart(ShopItem item) {
		this.items.clear();
		return true;
	}

}
