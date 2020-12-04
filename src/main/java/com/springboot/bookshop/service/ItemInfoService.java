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
	
	public List<ItemInfo> getItemByGroups(List<ItemInfo> allItems,int groupIndex){
		//List<ItemInfo> allItems = this.itemInfoRepository.findAll();
		int allSize = allItems.size();
		if(allSize <= (groupIndex-1) *20) {
			System.out.println("Return first 20..");
			return allItems.subList(0, allSize >= 20 ? 20 : allSize);
		}
		
		if(allSize < groupIndex*20) {
			System.out.println("Return partial");
			return allItems.subList((groupIndex-1) *20, allSize);
		}
		
		System.out.println("Return selected");
		return allItems.subList((groupIndex-1) *20, groupIndex*20);
	}


}
