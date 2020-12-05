package com.springboot.bookshop.controller;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@Scope("session")
public class IndexController {

	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "redirect:/";
	}
}
