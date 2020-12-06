package com.springboot.bookshop.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.bookshop.constant.enums.AccountType;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.service.SalesService;
import com.springboot.bookshop.service.UserService;
import com.springboot.bookshop.utils.ResponseBuilder;





@Controller
@RequestMapping("/admin_ops")
@Scope("session")
public class AdminDashboardController {

	@Autowired
	private ItemInfoService itemInfoService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private Visitor visitor;

	@Autowired
	private ResponseBuilder responseBuilder;
	
	@Autowired
	private SalesService salesService;


	@GetMapping("/{id}")
	public String getCart(@PathVariable(value = "id")String id, Model model) {
		if(this.visitor.getPermission() != AccountType.ADMIN) {
			return "redirect:/login";
		}
		ItemInfo targetItem = this.itemInfoService.findByProductId(id).orElse(null);
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("productId", targetItem.getProductId());
		System.out.println("I will redirect to show_Item");

		
		return ("show_Item");
	}
	
	
	// get all users
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		if(this.visitor.getPermission() != AccountType.ADMIN) {
			return "redirect:/login";
		}
		List<User> parameters = this.userService.findAll();
		model.addAttribute("parameters",parameters);	
		return ("showUsers");
	}
	
	
	// redirect back to admin_ops page
	@GetMapping("/dashboard")
	public String dashboard() {
		if(this.visitor.getPermission() != AccountType.ADMIN) {
			return "redirect:/login";
		}
		return "admin_ops";
	}
	
	// redirect to product_sales page
	@GetMapping("/product_sales")
	public String productSalesRoute() {
		if(this.visitor.getPermission() != AccountType.ADMIN) {
			return "redirect:/login";
		}
		return "products_sales";
	}
	
	
	// get products_sales for give month and year
	@SuppressWarnings("unchecked")
	@GetMapping("/getSales")
	public String getSalesBydate(@RequestParam("year")String year, @RequestParam("month")String month, Model model) {
		if(this.visitor.getPermission() != AccountType.ADMIN) {
			return "redirect:/login";
		}
		System.out.println("date passed is:" + month + " " + year);
		List<Object> list = new ArrayList<>();
		List<Object> soldItems = salesService.getAllByDate(Integer.parseInt(year), Integer.parseInt(month));
		for(Sales item : (List<Sales>)(Object)soldItems) {
			Optional<ItemInfo> items = itemInfoService.findByProductId(item.getItemId());	
			items.ifPresent(foundUpdateObject -> list.add(foundUpdateObject));
		}
		model.addAttribute("parameters", list);
		
		return "products_sales";
	}
}

















