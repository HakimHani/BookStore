package com.springboot.bookshop.controller;

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

import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.utils.DataValidation;
import com.springboot.bookshop.utils.IdentificationGenerator;



@RestController
@Scope("session")
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private IdentificationGenerator idGenerator;
	
	@Autowired
	private Visitor visitor;

	// get all users
	@GetMapping
	public List<Address> getAllAddress() {
		return this.addressService.findAll();
	}

	// get user by id
	@GetMapping("/{email}")
	public List<Address> getAddressByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.addressService.findAllByEmail(cutomerEmail);
	}

	// create user
	@PostMapping("/create/{email}")
	public Address createAddress(@RequestBody Address address) {
		/*
		if(address.getFirstName() == null) {
			System.out.println("No first name provided");
			address.setFirstName("fixed");
		}*/
		DataValidation validate = new DataValidation();
		boolean validAddress = validate.validateAddress(address);
		if(!validAddress) 
			return null;
		
		if(this.visitor.getUser() != null) {
			address.setEmail(this.visitor.getUser().getEmail());
		}
		address.setAddressId(idGenerator.generateAddressId());
		this.addressService.save(address);
		return address;
	}

	// update user
	@PutMapping("/modify/{addressId}")
	public Address updateAddress(@RequestBody Address address, @PathVariable ("addressId") String addressId) {
	
		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
		}
		existingAddress.setFirstName(address.getFirstName());
		existingAddress.setLastName(address.getLastName());
		existingAddress.setAddressOne(address.getAddressOne());
		existingAddress.setAddressTwo(address.getAddressTwo()); 
		existingAddress.setCity(address.getCity());
		existingAddress.setCountry(address.getCountry());
		existingAddress.setState(address.getState());
		existingAddress.setPostal(address.getPostal());
		existingAddress.setPhone(address.getPhone()); 
		return this.addressService.save(existingAddress);
	}

	// delete user by id
	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<Address> deleteAddress(@PathVariable ("addressId") String addressId){
		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId:" + addressId);
			throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
		}
		this.addressService.delete(existingAddress);
		return ResponseEntity.ok().build();
	}	
}

