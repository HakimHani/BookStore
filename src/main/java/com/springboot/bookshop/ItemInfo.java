package com.springboot.bookshop;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="iteminfo")
public class ItemInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	private long id;
	
	@Column(name="item_name", nullable = false, unique = true)
	private String itemName;

	@Column(name="brand", nullable = false)
	private String brand;

	@Column(name="price", nullable = false)
	private double price;
	
	@Column(name="sizes", nullable = false)
	private String sizes;
	
	@Column(name="category", nullable = false)
	private String category;
	
	@Column(name="max_cart_number", nullable = false)
	private int maxCartNumber;
	
	@Column(name="release_date", nullable = false)
	private String releaseDate;
	
	@Column(name="inventory", nullable = false)
	private double inventory;
	
	@Column(name="avaliable", nullable = false)
	private boolean isAvaliable;

	@Column(name="in_stock", nullable = false)
	private boolean isInstock;
	
	@Column(name="sku", nullable = false, unique = true)
	private String sku;
	
	@Column(name="sales", nullable = false)
	private int salesCount;



	public ItemInfo() {

	}

	public ItemInfo(String itemName, String brand, double price, String sizes, String category, int maxCartNumber, String releaseDate, double inventory, boolean isAvaliable, boolean isInstock, String sku, int salesCount) {
		super();
		this.itemName = itemName;
		this.brand = brand;
		this.price = price;
		this.sizes = sizes;
		this.category = category;
		this.maxCartNumber = maxCartNumber;
		this.releaseDate = releaseDate;
		this.inventory = inventory;
		this.isAvaliable = isAvaliable;
		this.isInstock = isInstock;
		this.sku = sku;
		this.salesCount = salesCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getMaxCartNumber() {
		return maxCartNumber;
	}

	public void setMaxCartNumber(int maxCartNumber) {
		this.maxCartNumber = maxCartNumber;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getInventory() {
		return inventory;
	}

	public void setInventory(double inventory) {
		this.inventory = inventory;
	}

	public boolean isAvaliable() {
		return isAvaliable;
	}

	public void setAvaliable(boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}

	public boolean isInstock() {
		return isInstock;
	}

	public void setInstock(boolean isInstock) {
		this.isInstock = isInstock;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}







}
