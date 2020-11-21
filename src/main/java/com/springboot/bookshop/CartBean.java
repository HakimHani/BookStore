package main.java.com.springboot.bookshop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CartBean {

	private Map<ItemInfo, Integer> books;

	public CartBean(){
		this(new HashMap<ItemInfo, Integer>());
	}

	public CartBean(HashMap<ItemInfo, Integer> books) {
		this.books = books;
	}
	
	public Map<ItemInfo, Integer> getbooks()
	{
		return books;
	}
	
	public void addBook(ItemInfo book){
		if(books.containsKey(book))
			books.put(book, books.get(book) + 1);
		else
			books.put(book, 1);
	}
	
	public void addBookAmount(ItemInfo book, int amount){
		if(books.containsKey(book))
			books.put(book, books.get(book) + amount);
		else
			books.put(book, amount);
	}
	
	public void removeBook(ItemInfo book){
		if(books.get(book)==1)
			books.remove(book, 1);
		else
			books.put(book, books.get(book) - 1);
	}
	
	public void setBookAmount(ItemInfo book, int amount) {
		if (amount > 0)
		books.put(book, amount);
	}
	
	public double getSubtotal(){
		double result = 0;
		Iterator <Map.Entry<ItemInfo, Integer>> it = books.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<ItemInfo, Integer> entry = it.next();
		    result = result + (entry.getKey().getPrice() * entry.getValue());
		}
		return result;
	}
	
	public double getTotal(){
		return this.getSubtotal() * 1.13;
	}
	
	public void clearCart()
	{
		this.books = new HashMap<ItemInfo, Integer>();
	}
	
	
}
