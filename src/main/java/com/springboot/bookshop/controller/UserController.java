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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.service.UserService;
import com.springboot.bookshop.utils.ResponseBuilder;



@RestController
@RequestMapping("/api/users")
@Scope("session")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private Visitor visitor;

	@Autowired
	private ResponseBuilder responseBuilder;

	// get all users
	@GetMapping
	public List<User> getAllUsers() {
		return this.userService.findAll();
	}

	// get user by id
	@GetMapping("/{email}")
	public User getUserByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return this.userService.findByEmail(cutomerEmail)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + cutomerEmail));
	}

	// create user
	@PostMapping("/")
	public ResponseEntity<Object> createUser(@RequestBody User customer) {

		if(this.userService.findByEmail(customer.getEmail()).orElse(null) != null) {
			//System.out.println(this.userService.findByEmail(customer.getEmail()));
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","CUSTOMER ALREADY EXISTS", null), HttpStatus.OK);
		}
		this.userService.save(customer);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","User created", customer), HttpStatus.OK);
	}

	@PostMapping("/updatebilling")
	public ResponseEntity<Object> updateDefaultBillingAddress(@RequestParam String addressId) {


		if(this.visitor.getUser() == null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","INVALID LOGIN STATE", null), HttpStatus.OK);
		}

		User existingUser = this.userService.findByEmail(this.visitor.getUser().getEmail()).orElse(null);
		if(existingUser == null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","USER NOT FOUND", null), HttpStatus.OK);
		}
		//.orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + this.visitor.getUser().getEmail()));


		if(addressId.equals("reset")) {
			existingUser.setDefaultBillingAddr(null);
			this.userService.save(existingUser);
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Successfully reset user", null), HttpStatus.OK);
		}


		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			//throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ADDRESS NOT FOUND", null), HttpStatus.OK);
		}

		if(!existingAddress.getEmail().equals(this.visitor.getUser().getEmail())) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ADDRESS EMAIL NOT MATCHED", null), HttpStatus.OK);
		}

		existingUser.setDefaultBillingAddr(addressId);
		this.userService.save(existingUser);

		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","User default billing updated", null), HttpStatus.OK);
	}

	@PostMapping("/updateshipping")
	public ResponseEntity<Object> updateDefaultShippingAddress(@RequestParam String addressId) {


		if(this.visitor.getUser() == null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","INVALID LOGIN STATE", null), HttpStatus.OK);
		}

		User existingUser = this.userService.findByEmail(this.visitor.getUser().getEmail()).orElse(null);
		if(existingUser == null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","USER NOT FOUND", null), HttpStatus.OK);
		}
		//.orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + this.visitor.getUser().getEmail()));


		if(addressId.equals("reset")) {
			existingUser.setDefaultShippingAddr(null);
			this.userService.save(existingUser);
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Successfully reset user shipping", null), HttpStatus.OK);
		}


		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			//throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ADDRESS NOT FOUND", null), HttpStatus.OK);
		}

		if(!existingAddress.getEmail().equals(this.visitor.getUser().getEmail())) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","ADDRESS EMAIL NOT MATCHED", null), HttpStatus.OK);
		}

		existingUser.setDefaultShippingAddr(addressId);
		this.userService.save(existingUser);

		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","User default shipping updated", null), HttpStatus.OK);
	}


	// update user
	@PutMapping("/update")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		User existingUser = this.userService.findByEmail(this.visitor.getUser().getEmail()).orElse(null);
		//	.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		if(existingUser == null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","USER NOT FOUND", null), HttpStatus.OK);
		}
		System.out.println(user.getFirstName());
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setPhone(user.getPhone());
		this.userService.save(existingUser);
		this.visitor.setUser(existingUser);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Successfully updated user", null), HttpStatus.OK);
	}

	// update user
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable ("id") long userId) {
		User existingUser = this.userService.findById(userId).orElse(null);
		if(existingUser == null) {
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Failed","USER NOT FOUND", null), HttpStatus.OK);
		}
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		this.userService.save(existingUser);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","Succesfully updated user by id", null), HttpStatus.OK);
	}

	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable ("id") long userId){
		User existingUser = this.userService.findById(userId).orElse(null);
		if(existingUser == null){
			return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","USER NOT FOUND", null), HttpStatus.OK);
		}
				//.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		this.userService.delete(existingUser);
		return new ResponseEntity<Object>(responseBuilder.itemInfoResponse("Success","User deleted", null), HttpStatus.OK);
		//return ResponseEntity.ok().build();
	}
}
