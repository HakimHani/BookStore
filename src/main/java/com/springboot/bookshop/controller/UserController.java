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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.service.UserService;



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
	public String createUser(@RequestBody User customer) {
		
		if(this.userService.findByEmail(customer.getEmail()).orElse(null) != null) {
			System.out.println(this.userService.findByEmail(customer.getEmail()));
			return "Customer already exist";
		}
		this.userService.save(customer);
		return "New customer created";
	}
	
	@PostMapping("/updatebilling")
	public String updateDefaultBillingAddress(@RequestParam String addressId) {
		
		
		if(this.visitor.getUser() == null) {
			return "No user logged in";
		}
		
		User existingUser = this.userService.findByEmail(this.visitor.getUser().getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + this.visitor.getUser().getEmail()));
		
		
		if(addressId.equals("reset")) {
			existingUser.setDefaultBillingAddr(null);
			this.userService.save(existingUser);
			return "Successfully reset billing address";
		}
		
		
		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
		}
		
		if(!existingAddress.getEmail().equals(this.visitor.getUser().getEmail())) {
			return "Address email does not match user";
		}
		
		existingUser.setDefaultBillingAddr(addressId);
		this.userService.save(existingUser);
		
		return "Successfully update billing address";
	}
	
	@PostMapping("/updateshipping")
	public String updateDefaultShippingAddress(@RequestParam String addressId) {
		
		
		if(this.visitor.getUser() == null) {
			return "No user logged in";
		}
		
		User existingUser = this.userService.findByEmail(this.visitor.getUser().getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + this.visitor.getUser().getEmail()));
		
		
		if(addressId.equals("reset")) {
			existingUser.setDefaultBillingAddr(null);
			this.userService.save(existingUser);
			return "Successfully reset billing address";
		}
		
		
		Address existingAddress = this.addressService.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
		}
		
		if(!existingAddress.getEmail().equals(this.visitor.getUser().getEmail())) {
			return "Address email does not match user";
		}
		
		existingUser.setDefaultShippingAddr(addressId);
		this.userService.save(existingUser);
		
		return "Successfully update shipping address";
	}
	
	// update user
	@PutMapping("/update")
	public String updateUser(@RequestBody User user) {
		User existingUser = this.userService.findByEmail(this.visitor.getUser().getEmail()).orElse(null);
		//	.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		if(existingUser == null) {
			return "Failed";
		}
			System.out.println(user.getFirstName());
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 existingUser.setPhone(user.getPhone());
		 this.userService.save(existingUser);
		 this.visitor.setUser(existingUser);
		 return "Success";
	}
	
	// update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable ("id") long userId) {
		User existingUser = this.userService.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 return this.userService.save(existingUser);
	}
	
	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
		User existingUser = this.userService.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		 this.userService.delete(existingUser);
		 return ResponseEntity.ok().build();
	}
}
