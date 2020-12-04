package com.springboot.bookshop.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.Review;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.service.ReviewService;

@Controller
@Scope("session")

public class VisitorController {

	//private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	
	@Autowired
	private Visitor visitor;
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@Autowired
	private ReviewService reviewService;



	@GetMapping(path = "/")
	public String homePage(Model model)
	{
		
		
		List<ItemInfo> allItems = this.itemInfoService.findAll();
		List<ItemInfo> items = this.itemInfoService.getItemByGroups(allItems,1);
		System.out.println(items.get(0).getImgurl());
		model.addAttribute("items",items);
		model.addAttribute("visitor",this.visitor);
		
		
		
		List<ItemInfo> best = this.itemInfoService.findByOrderBySalesCountDesc();
		best = best.subList(0,best.size() >= 4 ? 4 : best.size());
		model.addAttribute("best",best);
		
		return "products-home";

		
	}
	
	@GetMapping(path = "/products")
	public String products(Model model)
	{
		List<ItemInfo> allItems = itemInfoService.findAll();
		//List<ItemInfo> items = itemInfoRepository.findAll();
		List<ItemInfo> items = this.itemInfoService.getItemByGroups(allItems,1);
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
		List<ItemInfo> items = this.itemInfoService.getItemByGroups(allItems,groupIndex);
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
	
	



}
