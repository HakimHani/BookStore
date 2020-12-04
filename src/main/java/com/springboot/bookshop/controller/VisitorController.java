package com.springboot.bookshop.controller;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.Review;
import com.springboot.bookshop.model.ShopItem;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.repo.ReviewRepository;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.service.ReviewService;

@Controller
@Scope("session")

public class VisitorController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	
	@Autowired
	private Visitor visitor;
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/visit")
	public String visiting() {

		


		String message = "version 123 \n";

		if(visitor.getUser() == null) {
			message = message + "Not logged in \n";
		}else {
			message = message + "Logged in as " + visitor.getUser().getEmail() + "\n";
		}

		message = message + "Initial visite timestamp: " + sdf.format(visitor.getFirstTS()) + "\n";
		message = message + "Last visit timestamp: " + sdf.format(visitor.getLatestTS()) + "\n";
		message = message + "Cart size: " + visitor.getCart().getItems().size() + "\n";


		if(visitor.getCart().getItems().size() > 0) {
			List<ShopItem> items = visitor.getCart().getItems();
			for(int i=0; i < items.size(); i++) {
				ShopItem item = items.get(i);
				message = message + "item: " + item.getItemName() + "\n";
				message = message + "itemSessionId: " + item.getItemSessionId() + "\n";
			}
		}




		/*
		List<String> messages = new ArrayList<String>();
		messages.add("Visitor first: " + sdf.format(visitor.getFirstTS()));
		messages.add("Visitor latest: " + sdf.format(visitor.getLatestTS()));

		return messages.toString();
		 */
		visitor.refresh();
		return message;

	}

	@GetMapping(path = "/")
	public String homePage(Model model)
	{
		
		
		List<ItemInfo> allItems = itemInfoService.findAll();
		List<ItemInfo> items = getItemByGroups(allItems,1);
		System.out.println(items.get(0).getImgurl());
		model.addAttribute("items",items);
		model.addAttribute("visitor",this.visitor);
		
		
		
		List<ItemInfo> best = this.itemInfoService.findByOrderBySalesCountDesc();
		best = best.subList(0,best.size() >= 4 ? 4 : best.size());
		model.addAttribute("best",best);
		
		return "products-home";

		/*
		JSONObject response = new JSONObject();

		if(visitor.getUser() == null) {
			response.put("isLoggedIn", "false");
		}else {
			response.put("isLoggedIn", "true");
		}


		response.put("aa", sdf.format(visitor.getFirstTS()));
		response.put("aa", sdf.format(visitor.getLatestTS()));
		response.put("cart", ""+visitor.getCart().getItems().size());


		if(visitor.getCart().getItems().size() > 0) {
			List<ShopItem> items = visitor.getCart().getItems();
			for(int i=0; i < items.size(); i++) {
				JSONObject cartItem = new JSONObject();
				ShopItem item = items.get(i);
				cartItem.put("item", item.getItemName());
				cartItem.put("itemSessionId", item.getItemSessionId());
			}
			response.put("items", items);
		}
		//entity.put("aa", "bb");

		return response.toString();
		*/
		
		
		//toLogin
		/*
		if(this.visitor.getUser() != null) {
			return "redirect:/index";
		}
		
		
		model.addAttribute("visitor",this.visitor);
		return "login";
		*/
		
		
	}
	
	@GetMapping(path = "/products")
	public String products(Model model)
	{
		List<ItemInfo> allItems = itemInfoService.findAll();
		//List<ItemInfo> items = itemInfoRepository.findAll();
		List<ItemInfo> items = getItemByGroups(allItems,1);
		model.addAttribute("items",items);
		model.addAttribute("visitor",this.visitor);
		model.addAttribute("pageCount",allItems.size()/20);
		return "products";

	}
	
	@GetMapping(path = "/products/{groupIndex}")
	public String productsByPage(@PathVariable (value = "groupIndex") int groupIndex,Model model)
	{
			
		//List<ItemInfo> items = itemInfoRepository.findAll();
		List<ItemInfo> allItems = itemInfoService.findAll();
		List<ItemInfo> items = getItemByGroups(allItems,groupIndex);
		model.addAttribute("items",items);
		model.addAttribute("visitor",this.visitor);
		model.addAttribute("pageCount",allItems.size()/20);
		return "products";

	}
	
	@GetMapping(path = "/product/{productId}")
	public String productDetail(@PathVariable (value = "productId") String productId,Model model)
	{
			
		//List<ItemInfo> items = itemInfoRepository.findAll();
		ItemInfo item = itemInfoService.findByProductId(productId).orElse(null);
		if(item == null) {
			return "redirect:/";
		}
		model.addAttribute("item",item);
		model.addAttribute("visitor",this.visitor);
		
		List<ItemInfo> recommands = itemInfoService.findAllByCategory(item.getCategory());
		if(recommands.size() > 2) {
			recommands = recommands.subList(0, 3);
		}
		model.addAttribute("recommands",recommands);
		
		List<Review> reviews = reviewService.findAllByProductId(productId);
		model.addAttribute("reviews",reviews);
		
		return "product-detail";

	}
	
	
	@GetMapping(path = "/best-sellers")
	public String productsBestSellers(Model model)
	{
			
		//List<ItemInfo> items = itemInfoRepository.findAll();
		List<ItemInfo> best = this.itemInfoService.findByOrderBySalesCountDesc();
		List<ItemInfo> items = best.subList(0,best.size() < 20 ? best.size() : 19);
		//List<ItemInfo> items = best.subList(best.size()-21 >= 0?best.size()-21:0, best.size()-1);
		model.addAttribute("items",items);
		model.addAttribute("visitor",this.visitor);
		return "products";

	}
	
	
	@GetMapping(path = "/login")
	public String redirectToLogin(Model model)
	{
		
		if(this.visitor.getUser() != null) {
			return "redirect:/index";
		}
			
		model.addAttribute("visitor",this.visitor);
		return "login";
		
	}
	
	@GetMapping(path = "/cart")
	public String cart(Model model)
	{
				
		model.addAttribute("cartData",this.visitor.getCart());
		model.addAttribute("visitor",this.visitor);
		return "cart";
		
	}
	
	
	List<ItemInfo> getItemByGroups(List<ItemInfo> allItems,int groupIndex){
		//List<ItemInfo> allItems = this.itemInfoRepository.findAll();
		int allSize = allItems.size();
		if(allSize <= (groupIndex-1) *20) {
			System.out.println("Return first 20..");
			return allItems.subList(0, allSize >= 20 ? 20 : allSize);
		}
		
		if(allSize < groupIndex*20) {
			System.out.println("Return partial");
			return allItems.subList((groupIndex-1) *20, allSize);
		}
		
		System.out.println("Return selected");
		return allItems.subList((groupIndex-1) *20, groupIndex*20);
	}


}
