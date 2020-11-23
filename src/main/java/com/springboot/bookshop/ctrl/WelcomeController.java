package com.springboot.bookshop.ctrl;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.Visitor;

@RestController
@Scope("session")

public class WelcomeController {
	
	 private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	 @Autowired
	 private Visitor visitor;

	@GetMapping("/welcome")
	public String welcome(Model model, HttpSession session) {
		
		//Customer customer = new Customer();
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("VISITOR_TIMESTAMP");

		visitor.refresh();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (messages == null) {
			messages = new ArrayList<>();
		}
		
		messages.add("Visitor time stamp: " + sdf.format(timestamp));
		messages.add("Visitor info: " + sdf.format(visitor.getFirstTS()));
		
		
		
		session.setAttribute("VISITOR_TIMESTAMP", messages);
		model.addAttribute("VISITOR_TIMESTAMP", messages);
		return messages.toString();
		
		
	}
	
	
}
