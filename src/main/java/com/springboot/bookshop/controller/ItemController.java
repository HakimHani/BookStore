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
public class ItemController {

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
		//ModelAndView model = new ModelAndView();
		ItemInfo targetItem = this.itemInfoService.findByProductId(id).orElse(null);
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("productId", targetItem.getProductId());
		//mapData.forEach((key, val) -> parameters.put(key, val[0]));
		System.out.println("I will redirect to show_Item");
		//model.addAttribute("parameters",parameters);
		
		return ("show_Item");
	}
	
	
	// get all users
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		List<User> parameters = this.userService.findAll();
//		List<> parameters = new HashMap<String, List<String>>();
//		System.out.println("users number" + users.size());
//		for(User user: users) {
//			parameters.put("Id", user.getId()+ "");
//			parameters.put("First Name", user.getFirstName());
//			parameters.put("Lat Name", user.getLastName());
//			parameters.put("Email", user.getEmail());
//			parameters.put("Phone Number", user.getPhone());
//		}
		model.addAttribute("parameters",parameters);
		
		return ("showUsers");
	}


}

















