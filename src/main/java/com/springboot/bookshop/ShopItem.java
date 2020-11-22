package com.springboot.bookshop;

import java.util.UUID;

public class ShopItem {
	
	private String itemName;
	private String itemLabel;
	private double itemPrice;
	private String itemSku;
	private String itemSize;
	private String sizeSku;
	private String itemSessionId;
	
	public ShopItem(){
		
	}
	
	public ShopItem(String itemName, String itemLabel, double itemPrice, String itemSku, String itemSize, String sizeSku,String itemSessionId){
		this.itemName = itemName;
		this.itemLabel = itemLabel;
		this.itemPrice = itemPrice;
		this.itemSku = itemSku;
		this.itemSize = itemSize;
		this.sizeSku = sizeSku;
		this.itemSessionId = itemSessionId;
	}
	
	public ShopItem(ItemInfo itemInfo){
		this.itemName = itemInfo.getItemName();
		this.itemLabel = itemInfo.getItemLabel();
		this.itemPrice = itemInfo.getPrice();
		this.itemSku = itemInfo.getSku();
		this.itemSize = itemInfo.getSizes();
		this.sizeSku = itemInfo.getSizeSku();
		this.itemSessionId = UUID.randomUUID().toString().replace("-", "");
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public String getSizeSku() {
		return sizeSku;
	}

	public void setSizeSku(String sizeSku) {
		this.sizeSku = sizeSku;
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
