package com.springboot.bookshop.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.SalesService;

@RestController
@RequestMapping("/api/sales")
@Scope("session")
public class SalesController {



	
	@Autowired
	private SalesService saleService;

	@Autowired
	private Visitor visitor;


	
	//Get All sales
	@GetMapping("/")
	public List<Sales> getAllSales() {
		return  this.saleService.findAll();
	}
	

	
	//Get All sales result of specific item by itemId
	@GetMapping("/product/{productId}")
	public List<Sales> getReviewByEmail(@PathVariable (value = "productId") String productId) {
		return  this.saleService.findAllByItemId(productId);
	}

	
	
	//Filter sales by montj
	@GetMapping("/detail")
	public List<Sales> getReviewByProductId(@RequestParam String productId,@RequestParam int month) {
		List<Sales> productSales = this.saleService.findAllByItemId(productId);
		List<Sales> result = new ArrayList<Sales>();
		for(Sales sale : productSales) {
			Date saleDate = sale.getDate();
			LocalDate lD = saleDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int mN = lD.getMonthValue();
			//System.out.println("month num " + mN + " -> " + month);
			if(mN == month) {
				result.add(sale);
				//productSales.remove(index);
				//System.out.println("filter sale");
			}
			//Need validation on year as well -yikun
		}
		
		return result;
	}




}
