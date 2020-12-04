package com.springboot.bookshop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.bookshop.model.Visitor;





@Controller
@RequestMapping("/cartpage")
@Scope("session")
public class CartPageController {



	@Autowired
	private Visitor visitor;


	@GetMapping
	public String getCart(Model model) {
		if(visitor.getUser() == null) {
			System.out.println("Cart page: no user");
		}
		model.addAttribute("cart",this.visitor.getCart().getItems());
		return "cart-text";
	}


}
