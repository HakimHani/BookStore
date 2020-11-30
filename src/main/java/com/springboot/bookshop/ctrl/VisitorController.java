package com.springboot.bookshop.ctrl;

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
import org.springframework.web.bind.annotation.RestController;


import com.springboot.bookshop.ShopItem;
import com.springboot.bookshop.Visitor;

@Controller
@Scope("session")

public class VisitorController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	@Autowired
	private Visitor visitor;

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
	public String redirectToLogin(Model model)
	{


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
		
		if(this.visitor.getUser() != null) {
			return "redirect:/index";
		}
		
		
		model.addAttribute("visitor",this.visitor);
		return "login";
		
		
	}


}
