package com.springboot.bookshop.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

	
	ResponseBuilder(){
		
	}
	
	public Map<String, Object> itemInfoResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	
	public Map<String, Object> AddressResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	public Map<String, Object> BillingResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	public Map<String, Object> CheckoutResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	public Map<String, Object> cartResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	public Map<String, Object> reviewResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	public Map<String, Object> userResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
	public Map<String, Object> accountResponse(String status, String message, Object data) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		json.put("item", data);
		return json;
	}
	
}
