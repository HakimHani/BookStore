package com.springboot.bookshop.ctrl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.bookshop.ShopItem;
import com.springboot.bookshop.Visitor;

@Controller
@Scope("session")
public class IndexController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	@Autowired
	private Visitor visitor;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		if(visitor == null) {
			visitor = new Visitor();
		}

		JSONObject response = new JSONObject();

		if(visitor.getUser() == null) {
			response.put("isLoggedIn", "false");
			model.addAttribute("loginStatus", false);
		}else {
			response.put("isLoggedIn", "true");
			model.addAttribute("loginStatus", true);
			model.addAttribute("userName", visitor.getUser().getEmail());
		}


		response.put("aa", sdf.format(visitor.getFirstTS()));
		response.put("aa", sdf.format(visitor.getLatestTS()));
		response.put("cart", ""+visitor.getCart().getItems().size());
		model.addAttribute("cartSize", visitor.getCart().getItems().size());

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
		
		model.addAttribute("data", response.toString());
		return "index";
	}
}
