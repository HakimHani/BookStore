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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.bookshop.ShopItem;
import com.springboot.bookshop.Visitor;

@RestController
@Scope("session")

public class VisitorController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	@Autowired
	private Visitor visitor;

	@GetMapping("/visit")
	public String visiting() {

		if(visitor == null) {
			visitor = new Visitor();
		}


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

	@GetMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> sayHello()
	{
		
		if(visitor == null) {
			visitor = new Visitor();
		}

		List<JSONObject> entities = new ArrayList<JSONObject>();
		
		if(visitor.getUser() == null) {
			JSONObject loginStatus = new JSONObject("isLoggedIn", "false");
			entities.add(loginStatus);
		}else {
			JSONObject loginStatus = new JSONObject("isLoggedIn", "true");
			entities.add(loginStatus);
		}
		
		
		JSONObject fisrtVisit = new JSONObject("aa", sdf.format(visitor.getFirstTS()));
		JSONObject lastestVisit = new JSONObject("aa", sdf.format(visitor.getLatestTS()));
		JSONObject cart = new JSONObject("cart", ""+visitor.getCart().getItems().size());
		
		entities.add(fisrtVisit);
		entities.add(lastestVisit);
		entities.add(cart);
		if(visitor.getCart().getItems().size() > 0) {
			List<ShopItem> items = visitor.getCart().getItems();
			for(int i=0; i < items.size(); i++) {
				List<JSONObject> cartItem = new ArrayList<JSONObject>();
				ShopItem item = items.get(i);
				JSONObject itemName = new JSONObject("item", item.getItemName());
				JSONObject itemSessionId = new JSONObject("itemSessionId", item.getItemSessionId());
				cartItem.add(itemName);
				cartItem.add(itemSessionId);
			}
			JSONObject cI = new JSONObject("items", items.toString());
			entities.add(cI);
		}
		//entity.put("aa", "bb");
		
		return new ResponseEntity<Object>(entities, HttpStatus.OK);
	}


}
