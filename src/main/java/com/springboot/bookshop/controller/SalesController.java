package com.springboot.bookshop.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.constant.enums.AccountType;
import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.SalesService;
import com.springboot.bookshop.utils.ResponseBuilder;

@RestController
@RequestMapping("/api/sales")
@Scope("session")
public class SalesController {




	@Autowired
	private SalesService saleService;

	@Autowired
	private Visitor visitor;

	@Autowired
	private ResponseBuilder responseBuilder;



	//Get All sales
	@GetMapping("/")
	public ResponseEntity<Object> getAllSales() {
		if(this.visitor.getUser() == null) {
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, No Permission", 0, null), HttpStatus.OK);
		}

		switch(this.visitor.getPermission()){
		case ADMIN:
			List<Sales> tSales = this.saleService.findAll();
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", tSales.size(), tSales), HttpStatus.OK);
		case PARTNER:
			List<Object> pSales = this.saleService.findAllForPartner();
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", pSales.size(), pSales), HttpStatus.OK);

		default:
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, No Permission", 0, null), HttpStatus.OK);

		}


	}
	
	

	@GetMapping("/data/id")
	public ResponseEntity<Object> getAllSalesForPartner(@RequestParam Optional<String> productId) {

		String sProductId;
		if(this.visitor.getUser() == null) {
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, No Permission", 0, null), HttpStatus.OK);
		}else if( !productId.isPresent() || productId.orElse(null).equals("")) {
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, Invalid pid", 0, null), HttpStatus.OK);
		}
		sProductId = productId.orElse(null);
		switch(this.visitor.getPermission()){
		case ADMIN:
			List<Sales> aSales = this.saleService.findAllByItemId(sProductId);
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", aSales.size(), aSales), HttpStatus.OK);
		case PARTNER:
			List<Object> pSales = this.saleService.findAllByItemIdForPartner(sProductId);
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", pSales.size(), pSales), HttpStatus.OK);
		default:
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, No Permission", 0, null), HttpStatus.OK);

		}


	}
	

	@GetMapping("/data/filter")
	public ResponseEntity<Object> getAllSalesForPartnerMonth(@RequestParam Optional<Integer> month,@RequestParam Optional<Integer> year,@RequestParam Optional<String> productId) {
		System.out.println("Get sales data by date and productId");
		int sMonth,sYear;
		String sProductId;

		if(this.visitor.getUser() == null) {
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, No Permission", 0, null), HttpStatus.OK);
		}else if( !productId.isPresent() || !month.isPresent() || !year.isPresent()) {
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, Insufficient param", 0, null), HttpStatus.OK);
		}
		sMonth = month.orElse(0);
		sYear = year.orElse(0);
		sProductId = productId.orElse(null);
		if(sMonth == 0 || sYear == 0 || sProductId.equals(null)) {
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, Invalid param", 0, null), HttpStatus.OK);
		}


		boolean isAll = sProductId.equals("all");

		switch(this.visitor.getPermission()){
		case ADMIN:
			if(isAll) {
				System.out.println("Show all");
				List<Object> tSales = this.saleService.getAllByDate(sYear,sMonth);
				return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", tSales.size(), tSales), HttpStatus.OK);
			}else {
				System.out.println("Show by id");
				List<Object> tSales = this.saleService.getAllByItemIdAndDate(sProductId,sYear,sMonth);
				return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", tSales.size(), tSales), HttpStatus.OK);
			}
		case PARTNER:
			if(isAll) {
				System.out.println("Show all");
				List<Object> tSales = this.saleService.getAllByDatePartner(sYear,sMonth);
				return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", tSales.size(), tSales), HttpStatus.OK);
			}else {
				System.out.println("Show by id");
				List<Object> tSales = this.saleService.getAllByItemIdAndDatePartner(sProductId,sYear,sMonth);
				return new ResponseEntity<Object>(responseBuilder.salesReponse("Success", tSales.size(), tSales), HttpStatus.OK);
			}
		default:
			return new ResponseEntity<Object>(responseBuilder.salesReponse("Failed, No Permission", 0, null), HttpStatus.OK);

		}




	}



	//Get All sales result of specific item by itemId
	@GetMapping("/product/{productId}")
	public List<Sales> getReviewByEmail(@PathVariable (value = "productId") String productId) {
		return this.saleService.findAllByItemId(productId);
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
