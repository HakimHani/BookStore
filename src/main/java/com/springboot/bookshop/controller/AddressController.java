package com.springboot.bookshop.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
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
import com.springboot.bookshop.utils.ResponseBuilder;



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
	
	@Autowired
	private DataValidation dataValidation;
	
	@Autowired
	private ResponseBuilder responseBuilder;

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
	public ResponseEntity<Object> createAddress(@RequestBody Address address) {

		if(this.visitor.getUser() != null) {
			address.setEmail(this.visitor.getUser().getEmail());
		}
		
		if(!dataValidation.validateAddress(address)) {
			return new ResponseEntity<Object>(responseBuilder.AddressResponse("Failed", "ADDRESS_VALIDATION_FAILED", address), HttpStatus.OK);
		}
		address.setAddressId(idGenerator.generateAddressId());
		this.addressService.save(address);
		
		return new ResponseEntity<Object>(responseBuilder.AddressResponse("Success", "Address created", address), HttpStatus.OK);
	}
	
	

	// update user
	@PutMapping("/modify")
	public ResponseEntity<Object> updateAddress(@RequestBody Address address) {
	
		
		if(!dataValidation.validateAddress(address)) {
			return new ResponseEntity<Object>(responseBuilder.AddressResponse("Failed", "ADDRESS_VALIDATION_FAILED", address), HttpStatus.OK);
		}
		
		
		Boolean modify = this.addressService.modify(address);
		if(!modify) {
			return new ResponseEntity<Object>(responseBuilder.AddressResponse("Failed", "Error modify address", address), HttpStatus.OK);
		}
		
		return new ResponseEntity<Object>(responseBuilder.AddressResponse("Success", "Address modified", address), HttpStatus.OK);
	}
	
	
	

	// delete user by id
	@DeleteMapping("/delete/")
	public ResponseEntity<Object> deleteAddress(@PathVariable ("addressId") String addressId){
		
		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId:" + addressId);
			return new ResponseEntity<Object>(responseBuilder.AddressResponse("Success", "Address is not found", null), HttpStatus.OK);
			//throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
		}
		
		this.addressService.delete(existingAddress);
		return new ResponseEntity<Object>(responseBuilder.AddressResponse("Success", "Address deleted", null), HttpStatus.OK);
	}	
	
	
	
}

