package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.repo.CheckoutRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;


@Service
public class ItemInfoService {
	private final ItemInfoRepository itemInfoRepository;


	public ItemInfoService(ItemInfoRepository itemInfoRepository) {
		this.itemInfoRepository = itemInfoRepository;
	}

	public List<ItemInfo> findAll(){
		return this.itemInfoRepository.findAll();
	}

	public Optional<ItemInfo> findById(Long id){
		return this.itemInfoRepository.findById(id);
	}
	
	public Optional<ItemInfo> findByProductId(String productId){
		return this.itemInfoRepository.findByProductId(productId);
	}
	
	public List<ItemInfo> findAllByBrand(String brand){
		return this.itemInfoRepository.findAllByBrand(brand);
	}
	
	public List<ItemInfo> findAllByCategory(String category){
		return this.itemInfoRepository.findAllByCategory(category);
	}
	
	
	public List<ItemInfo> findByOrderBySalesCountAsc(){
		return this.itemInfoRepository.findByOrderBySalesCountAsc();
	}
	
	public List<ItemInfo> findByOrderBySalesCountDesc(){
		return this.itemInfoRepository.findByOrderBySalesCountDesc();
	}

	public ItemInfo save(ItemInfo item){
		return this.itemInfoRepository.save(item);
	}
	
	public void delete(ItemInfo item){
		this.itemInfoRepository.delete(item);
	}


}
