package com.springboot.bookshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.service.ItemInfoService;
import com.springboot.bookshop.utils.IdentificationGenerator;
import com.springboot.bookshop.utils.ItemInfosParser;
import com.springboot.bookshop.utils.ResponseBuilder;





@RestController
@RequestMapping("/api/iteminfo")
@Scope("session")
public class ItemInfoController {

	@Autowired
	private ItemInfoService itemInfoService;

	@Autowired
	private IdentificationGenerator idGenerator;
	
	@Autowired
	private ResponseBuilder responseBuilder;



	// get all products
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

	// get all products
	@GetMapping("/all")
	public List<ItemInfo> getItemInfoBySku() {
		return this.itemInfoService.findAll();
	}
	
	// get products by author name
	@GetMapping("/brand/{itemBrand}")
	public List<ItemInfo> getItemInfoByBrand(@PathVariable (value = "itemBrand") String itemBrand) {
		System.out.println("Checking with " + itemBrand);
		return this.itemInfoService.findAllByBrand(itemBrand);		
	}

	// get products by category
	@GetMapping("/category/{itemCategory}")
	public List<ItemInfo> getItemInfoByCategory(@PathVariable (value = "itemCategory") String itemCategory) {
		System.out.println("Checking with " + itemCategory);
		return this.itemInfoService.findAllByCategory(itemCategory);
	}
	

	// create item
	@PostMapping("/create")
	public ResponseEntity<Object> createItemInfo(@RequestBody ItemInfo itemInfo) {

		/*if(itemInfo.getSku() == null || itemInfo.getSizes() == null || itemInfo.getSizeSku() == null) {
			return "wrong format";
		}*/
		//TODO: NEED VALIDATION
		itemInfo.setSku(idGenerator.generateAddressId().substring(0,6));
		itemInfo.setSizeSku("01");
		itemInfo.setProductId(itemInfo.getSku() + itemInfo.getSizeSku());
		itemInfo.setRate(0.00);
		itemInfo.setSize(" HARDCOVER");
		itemInfo.setItemLabel(itemInfo.getItemName());
		
		if(this.itemInfoService.findByProductId(itemInfo.getProductId()).orElse(null) != null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ITEM DUPLICATED", null), HttpStatus.OK);
		}

		
		this.itemInfoService.save(itemInfo);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Item created", itemInfo), HttpStatus.OK);
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
	
	
	

	// delete item by post
	@PostMapping("/delete/{productId}")
	public ResponseEntity<Object> deleteItemInfo(@PathVariable (value = "productId") String productId) {
		ItemInfo item = this.itemInfoService.findByProductId(productId).orElse(null);
		if(item == null) {
            return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ITEM NOT FOUND", null), HttpStatus.OK);
		}
		this.itemInfoService.delete(item);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Item deleted", item), HttpStatus.OK);
	}
	
	
	

	// update item info
	@PostMapping(path = "/update_inventory", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateItemInfo(@RequestBody ItemInfo itemInfo) {
		ItemInfo item = this.itemInfoService.findByProductId(itemInfo.getProductId()).orElse(null);
				//.orElseThrow(() -> new ResourceNotFoundException("Item not found with sku :" + updateInfo.get(0)));
			
		if(item == null) {
            return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ITEM NOT FOUND", null), HttpStatus.OK);
		}
		
		
		if(itemInfo.getInventory() < 0) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","WRONG INVENTORY FORMAT", null), HttpStatus.OK);
		}
		item.setInventory(itemInfo.getInventory());
		item.setItemName(itemInfo.getItemName());
		item.setItemLabel(itemInfo.getItemName());
		item.setPrice(itemInfo.getPrice());
		item.setAvaliable(itemInfo.isAvaliable());
		this.itemInfoService.save(item);

        return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Inventory updated", item), HttpStatus.OK);
	}
	
	

	// delete item by id through DELETEMAPPING
	@DeleteMapping("/{productId}")
	public ResponseEntity<Object> deleteUser(@PathVariable (value = "productId") String productId){ 
		
		ItemInfo item = this.itemInfoService.findByProductId(productId).orElse(null);
		if(item == null) {
            return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ITEM NOT FOUND", null), HttpStatus.OK);
		}
				//.orElseThrow(() -> new ResourceNotFoundException("Item not found with id :" + productId));
		this.itemInfoService.delete(item);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Item deleted", item), HttpStatus.OK);
		
	}
}
