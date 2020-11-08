package com.springboot.bookshop.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookshop.Address;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AddressRepository;



@RestController
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepo;

	// get all users
	@GetMapping
	public List<Address> getAllAddress() {
		return this.addressRepo.findAll();
	}

	// get user by id
	@GetMapping("/{email}")
	public List<Address> getAddressByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return  this.addressRepo.findAllByEmail(cutomerEmail);
	}

	// create user
	@PostMapping
	public String createAddress(@RequestBody Address address) {
		this.addressRepo.save(address);
		return "New address created";
	}

	// update user
	@PutMapping("/{id}")
	public Address updateAddress(@RequestBody Address address, @PathVariable ("id") long userId) {
		Address existingAddress = this.addressRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Address not found with id :" + userId));
		existingAddress.setFirstName(address.getFirstName());
		existingAddress.setLastName(address.getLastName());
		return this.addressRepo.save(existingAddress);
	}

	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Address> deleteAddress(@PathVariable ("id") long userId){
		Address existingAddress = this.addressRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Address not found with id :" + userId));
		this.addressRepo.delete(existingAddress);
		return ResponseEntity.ok().build();
	}
}
