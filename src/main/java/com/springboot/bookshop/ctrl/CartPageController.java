package com.springboot.bookshop.ctrl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.springboot.bookshop.Visitor;





@Controller
@RequestMapping("/cart")
@Scope("session")
public class CartPageController {



	@Autowired
	private Visitor visitor;

	
	@GetMapping
	//@ResponseBody
	public String getCart(Model model) {
		if(visitor == null) {
			visitor = new Visitor();
		}
		model.addAttribute("cart",this.visitor.getCart().getItems());
		return "cart";
	}

	
}
