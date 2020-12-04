package com.springboot.bookshop.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;

import com.springboot.bookshop.entity.ItemInfo;



public class ItemInfosParser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String itemName;

	private String brand;

	private double[] prices;

	private String[] sizes;

	private String category;

	private int[] maxCartNumbers;

	private String releaseDate;

	private double[] inventorys;

	private boolean[] isAvaliables;

	private String sku;

	
	public ItemInfosParser() {
		
	};

	public ItemInfosParser(String itemName, String brand, double[] prices, String[] sizes, String category, int[] maxCartNumbers, String releaseDate, double[] inventorys, boolean isAvaliables[], String sku) {
		super();
		System.out.println("construct parser");
		this.itemName = itemName;
		this.brand = brand;
		this.prices = prices;
		this.sizes = sizes;
		this.category = category;
		this.maxCartNumbers = maxCartNumbers;
		this.releaseDate = releaseDate;
		this.inventorys = inventorys;
		this.isAvaliables = isAvaliables;
		this.sku = sku;
	}

	
	
	public ArrayList<ItemInfo> parse() {
		System.out.println("parsing....item infos");
		System.out.println(
				"name " + this.itemName + "\n" +
						"brand " + this.brand + "\n" +
						"prices " + this.prices + "\n" +
						"sizes " + this.sizes + "\n" +
						"category " + this.category + "\n" +
						"maxCartNumbers " + this.maxCartNumbers + "\n" +
						"releaseDate " + this.releaseDate + "\n" +
						"inventorys " + this.inventorys + "\n" +
						"isAvaliables  " + this.isAvaliables + "\n" +
						"sku " + this.sku + "\n" 
				);
		int sizesLength = this.sizes.length;
		if(this.prices.length != sizesLength || this.maxCartNumbers.length != sizesLength || this.inventorys.length != sizesLength || this.isAvaliables.length != sizesLength) {
			return null;
		}
		ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();
		for(int i = 0; i < sizesLength; i++) {
			String productId = this.sku + "0" + i;
			String itemLabel = this.itemName + " " + this.sizes[i];
			double price = this.prices[i];
			int maxCartNumber = this.maxCartNumbers[i];
			String size = this.sizes[i];
			double inventory = this.inventorys[i];
			boolean isAvaliable = this.isAvaliables[i];
			boolean isInstock = inventory > 0;
			items.add(new ItemInfo(productId,  this.itemName, itemLabel, this.brand,  price, size, i+"" , this.category, maxCartNumber, this.releaseDate, inventory, isAvaliable, isInstock, this.sku, 0));
		}
		
		return items;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double[] getPrices() {
		return prices;
	}

	public void setPrices(double[] prices) {
		this.prices = prices;
	}

	public String[] getSizes() {
		return sizes;
	}

	public void setSizes(String[] sizes) {
		this.sizes = sizes;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int[] getMaxCartNumbers() {
		return maxCartNumbers;
	}

	public void setMaxCartNumbers(int[] maxCartNumbers) {
		this.maxCartNumbers = maxCartNumbers;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double[] getInventorys() {
		return inventorys;
	}

	public void setInventorys(double[] inventorys) {
		this.inventorys = inventorys;
	}

	public boolean[] getIsAvaliables() {
		return isAvaliables;
	}

	public void setIsAvaliables(boolean[] isAvaliables) {
		this.isAvaliables = isAvaliables;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	private List<String> parseStringToList(String column) {
		List<String> output = new ArrayList<>();
		String listString = column.substring(1, column.length() - 1);
		StringTokenizer stringTokenizer = new StringTokenizer(listString,",");
		while (stringTokenizer.hasMoreTokens()){
			output.add(stringTokenizer.nextToken());
		}
		return output;
	}
}
