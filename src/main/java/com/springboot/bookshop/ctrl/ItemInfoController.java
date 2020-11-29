package com.springboot.bookshop.ctrl;

import java.util.ArrayList;
import java.util.List;
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

import com.springboot.bookshop.ItemInfo;
import com.springboot.bookshop.ItemInfosParser;
import com.springboot.bookshop.exception.ResourceNotFoundException;

import com.springboot.bookshop.repo.ItemInfoRepository;





@RestController
@RequestMapping("/api/iteminfo")
@Scope("session")
public class ItemInfoController {

	@Autowired
	private ItemInfoRepository itemInfoRepository;





	// get all users
	@GetMapping
	public List<ItemInfo> getAllItemInfos() {
		return this.itemInfoRepository.findAll();
	}
	
	// get item by id
	@GetMapping("/{itemId}")
	public Optional<ItemInfo> getItemInfoById(@PathVariable (value = "itemId") String id) {
		return this.itemInfoRepository.findByProductId(id);
	}

	// get user by id
	@GetMapping("/all")
	public List<ItemInfo> getItemInfoBySku() {
		return this.itemInfoRepository.findAll();
	}
	
	// get user by id
	@GetMapping("/brand/{itemBrand}")
	public List<ItemInfo> getItemInfoByBrand(@PathVariable (value = "itemBrand") String itemBrand) {
		System.out.println("Checking with " + itemBrand);
		return this.itemInfoRepository.findAllByBrand(itemBrand);		
	}

	// get user by id
	@GetMapping("/category/{itemCategory}")
	public List<ItemInfo> getItemInfoByCategory(@PathVariable (value = "itemCategory") String itemCategory) {
		System.out.println("Checking with " + itemCategory);
		return this.itemInfoRepository.findAllByCategory(itemCategory);
	}

	// create user
	@PostMapping("/create")
	public String createItemInfo(@RequestBody ItemInfo itemInfo) {

		if(itemInfo.getSku() == null || itemInfo.getSizes() == null) {
			return "wrong format";
		}

		if(this.itemInfoRepository.findByProductId(itemInfo.getProductId()).orElse(null) != null) {
			return "ItemInfo already exist";
		}
		this.itemInfoRepository.save(itemInfo);
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
			this.itemInfoRepository.save(itemInfo);
		}

		return "New ItemInfo sets created";
	}

	// login user
	@PostMapping("/delete")
	public String deleteInfoInfo(@RequestBody ItemInfo itemInfo) {
		ItemInfo found = this.itemInfoRepository.findByProductId(itemInfo.getProductId()).orElse(null);
		if( found != null) {
			this.itemInfoRepository.delete(itemInfo);
			return "ItemInfo Removed";
		}
		return "ItemInfo not found";
	}

	// update user
	@PostMapping("/update_inventory")
	public ItemInfo updateItemInfo(@RequestBody List<String> updateInfo) {
		ItemInfo existingUser = this.itemInfoRepository.findByProductId(updateInfo.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with sku :" + updateInfo.get(0)));
		existingUser.setInventory(Integer.parseInt(updateInfo.get(1)));
		return this.itemInfoRepository.save(existingUser);
	}

	// delete user by id
	@DeleteMapping("/{sku}")
	public ResponseEntity<ItemInfo> deleteUser(@PathVariable ("productId") String productId){
		ItemInfo existingUser = this.itemInfoRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with id :" + productId));
		this.itemInfoRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
