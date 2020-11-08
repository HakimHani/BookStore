package com.springboot.bookshop;

import java.util.UUID;

public class ShopItem {
	
	private String itemName;
	private double itemPrice;
	private String itemSku;
	private String itemSize;
	private String itemSessionId;
	
	public ShopItem(){
		
	}
	
	public ShopItem(String itemName, double itemPrice, String itemSku, String itemSize,String itemSessionId){
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemSku = itemSku;
		this.itemSize = itemSize;
		this.itemSessionId = itemSessionId;
	}
	
	public ShopItem(ItemInfo itemInfo){
		this.itemName = itemInfo.getItemName();
		this.itemPrice = itemInfo.getPrice();
		this.itemSku = itemInfo.getSku();
		this.itemSize = itemInfo.getSizes();
		this.itemSessionId = UUID.randomUUID().toString().replace("-", "");
	}

	public String getItemSessionId() {
		return itemSessionId;
	}

	public void setItemSessionId(String itemSessionId) {
		this.itemSessionId = itemSessionId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemSku() {
		return itemSku;
	}

	public void setItemSku(String itemSku) {
		this.itemSku = itemSku;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

}
