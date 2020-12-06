package com.springboot.bookshop.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.bookshop.constant.enums.AccountType;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.service.ItemInfoService;
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
	
	
	
	@GetMapping("/dashboard")
	public String dashboard() {
		if(this.visitor.getPermission() != AccountType.ADMIN) {
			return "redirect:/login";
		}
		return "admin_ops";
	}


}

















