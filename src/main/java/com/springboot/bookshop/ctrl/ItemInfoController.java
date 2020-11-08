package com.springboot.bookshop.ctrl;

import java.util.List;

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

	// get user by id
	@GetMapping("/{sku}")
	public ItemInfo getItemInfoBySku(@PathVariable (value = "sku") String sku) {

		return this.itemInfoRepository.findBySku(sku)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with sku :" + sku));
	}

	// create user
	@PostMapping("/create")
	public String createItemInfo(@RequestBody ItemInfo itemInfo) {

		if(this.itemInfoRepository.findBySku(itemInfo.getSku()).orElse(null) != null) {
			return "ItemInfo already exist";
		}
		this.itemInfoRepository.save(itemInfo);
		return "New ItemInfo created";
	}

	// login user
	@PostMapping("/delete")
	public String deleteInfoInfo(@RequestBody ItemInfo itemInfo) {
		ItemInfo found = this.itemInfoRepository.findBySku(itemInfo.getSku()).orElse(null);
		if( found != null) {
				this.itemInfoRepository.delete(itemInfo);
				return "ItemInfo Removed";
		}
		return "ItemInfo not found";
	}

	// update user
	@PostMapping("/update_inventory")
	public ItemInfo updateItemInfo(@RequestBody List<String> updateInfo) {
		ItemInfo existingUser = this.itemInfoRepository.findBySku(updateInfo.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with sku :" + updateInfo.get(0)));
		existingUser.setInventory(Integer.parseInt(updateInfo.get(1)));
		return this.itemInfoRepository.save(existingUser);
	}

	// delete user by id
	@DeleteMapping("/{sku}")
	public ResponseEntity<ItemInfo> deleteUser(@PathVariable ("sku") String sku){
		ItemInfo existingUser = this.itemInfoRepository.findBySku(sku)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found with id :" + sku));
		this.itemInfoRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
