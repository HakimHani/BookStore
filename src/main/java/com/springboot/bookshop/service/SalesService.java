package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;


import com.springboot.bookshop.entity.Sales;
import com.springboot.bookshop.repo.SalesRepository;


@Service
public class SalesService {
	private final SalesRepository salesRepository;


	public SalesService(SalesRepository salesRepository) {
		this.salesRepository = salesRepository;
	}


	public List<Sales> findAll(){
		return this.salesRepository.findAll();
	}
	
	public List<Object> findAllForPartner(){
		return this.salesRepository.getAllSalesForPartner();
	}
	
	public List<Object> findAllByItemIdForPartner(String sProductId){
		return this.salesRepository.getAllByItemIdForPartner(sProductId);
	}
	
	public List<Object> getAllByDate(int sYear,int sMonth){
		return this.salesRepository.getAllByDate(sYear,sMonth);
	}
	
	public List<Object> getAllByItemIdAndDate(String sProductId,int sYear,int sMonth){
		return this.salesRepository.getAllByItemIdAndDate(sProductId,sYear,sMonth);
	}
	
	public List<Object> getAllByDatePartner(int sYear,int sMonth){
		return this.salesRepository.getAllByDatePartner(sYear,sMonth);
	}
	
	public List<Object> getAllByItemIdAndDatePartner(String sProductId,int sYear,int sMonth){
		return this.salesRepository.getAllByItemIdAndDatePartner(sProductId,sYear,sMonth);
	}

	public Optional<Sales> findById(Long id){
		return this.salesRepository.findById(id);
	}
	
	
	public Optional<Sales> findBySalesId(String salesId){
		return this.salesRepository.findBySalesId(salesId);
	}
	
	public Optional<Sales> findByCheckoutId(String checkoutId){
		return this.salesRepository.findByCheckoutId(checkoutId);
	}
	
	public List<Sales> findAllByCustomerEmail(String customerEmail){
		return this.salesRepository.findAllByCustomerEmail(customerEmail);
	}
	
	public List<Sales> findAllByItemId(String itemId){
		return this.salesRepository.findAllByItemId(itemId);
	}

	public Sales save(Sales sales){
		return this.salesRepository.save(sales);
	}
	
	public void delete(Sales sales){
		this.salesRepository.delete(sales);
	}


}
