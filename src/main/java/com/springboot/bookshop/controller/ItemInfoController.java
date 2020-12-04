package com.springboot.bookshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.ItemInfoRepository;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.utils.IdentificationGenerator;
import com.springboot.bookshop.utils.ItemInfosParser;





@RestController
@RequestMapping("/api/iteminfo")
@Scope("session")
public class ItemInfoController {

	@Autowired
	private ItemInfoService itemInfoService;

	@Autowired
	private IdentificationGenerator idGenerator;



	// get all users
	@GetMapping
	public List<ItemInfo> getAllItemInfos() {
		return this.itemInfoService.findAll();
	}
	
	@GetMapping("/all/{groupIndex}")
	public List<ItemInfo> getAllByGroup(@PathVariable (value = "groupIndex") int groupIndex) {
		List<ItemInfo> allItems = this.itemInfoService.findAll();
		int allSize = allItems.size();
		if(allSize <= (groupIndex-1) *20) {
			System.out.println("Return first 20..");
			return allItems.subList(0, 19);
		}
		
		if(allSize < groupIndex*20) {
			System.out.println("Return partial");
			return allItems.subList((groupIndex-1) *20, allSize-1);
		}
		
		System.out.println("Return selected");
		return allItems.subList((groupIndex-1) *20, groupIndex*20);

		//return this.itemInfoRepository.findByOrderBySalesCountAsc();
	}
	
	
	@GetMapping("/best-seller")
	public List<ItemInfo> getAllItemInfosBySales() {
		return this.itemInfoService.findByOrderBySalesCountDesc();
		//this.itemInfoRepository.findByOrderBySalesCountAsc();
	}
	
	// get item by id
	@GetMapping("/{itemId}")
	public Optional<ItemInfo> getItemInfoById(@PathVariable (value = "itemId") String id) {
		return this.itemInfoService.findByProductId(id);
	}

	// get user by id
	@GetMapping("/all")
	public List<ItemInfo> getItemInfoBySku() {
		return this.itemInfoService.findAll();
	}
	
	// get user by id
	@GetMapping("/brand/{itemBrand}")
	public List<ItemInfo> getItemInfoByBrand(@PathVariable (value = "itemBrand") String itemBrand) {
		System.out.println("Checking with " + itemBrand);
		return this.itemInfoService.findAllByBrand(itemBrand);		
	}

	// get user by id
	@GetMapping("/category/{itemCategory}")
	public List<ItemInfo> getItemInfoByCategory(@PathVariable (value = "itemCategory") String itemCategory) {
		System.out.println("Checking with " + itemCategory);
		return this.itemInfoService.findAllByCategory(itemCategory);
	}

	// create user
	@PostMapping("/create")
	public String createItemInfo(@RequestBody ItemInfo itemInfo) {

		/*if(itemInfo.getSku() == null || itemInfo.getSizes() == null || itemInfo.getSizeSku() == null) {
			return "wrong format";
		}*/
		itemInfo.setSku(idGenerator.generateAddressId().substring(0,6));
		itemInfo.setSizeSku("01");
		itemInfo.setProductId(itemInfo.getSku() + itemInfo.getSizeSku());
		itemInfo.setRate(0.00);

		if(this.itemInfoService.findByProductId(itemInfo.getProductId()).orElse(null) != null) {
			return "ItemInfo already exist";
		}
		
		itemInfo.setSize(" HARDCOVER");
		itemInfo.setItemLabel(itemInfo.getItemName());
		
		
		this.itemInfoService.save(itemInfo);
		return "New ItemInfo created";
	}

	@PostMapping("/masscreate")
	public String massCreateItemInfo(@RequestBody ItemInfosParser itemInfosParser) {
		System.out.println("POST mass create");
		ArrayList<ItemInfo> itemInfos = itemInfosParser.parse();
		if(itemInfos == null || itemInfos.size()  == 0) {
			return "Failed mass creating items";
		}

		for(ItemInfo itemInfo : itemInfos) {
			this.itemInfoService.save(itemInfo);
		}

		return "New ItemInfo sets created";
	}

	// login user
	@PostMapping("/delete/{productId}")
	public String deleteItemInfo(@PathVariable (value = "productId") String productId) {
		ItemInfo found = this.itemInfoService.findByProductId(productId).orElse(null);
		if( found != null) {
			System.out.println("item found");
			this.itemInfoService.delete(found);
			return "ItemInfo Removed";
		}
		return "ItemInfo not found";
	}

	// update item info
	@PostMapping("/update_inventory")
	public ItemInfo updateItemInfo(@RequestBody List<String> updateInfo) {
		ItemInfo item = this.itemInfoService.findByProductId(updateInfo.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with sku :" + updateInfo.get(0)));
		item.setInventory(Integer.parseInt(updateInfo.get(1)));
		return this.itemInfoService.save(item);
	}

	// delete user by id
	@DeleteMapping("/{productId}")
	public ResponseEntity<ItemInfo> deleteUser(@PathVariable (value = "productId") String productId){ 
		System.out.println("in the conroller to delete");
		ItemInfo existingUser = this.itemInfoService.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with id :" + productId));
		this.itemInfoService.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
