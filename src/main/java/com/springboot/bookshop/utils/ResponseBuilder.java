package com.springboot.bookshop.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

	
	ResponseBuilder(){
		
	}
	
	public Map<String, Object> itemInfoResponse(String status, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("item", data);
		return json;
	}
	
}
