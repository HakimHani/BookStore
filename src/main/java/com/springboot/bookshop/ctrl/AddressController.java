package com.springboot.bookshop.ctrl;

import java.util.List;
import java.util.Optional;

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
	@PostMapping("/create/{email}")
	public String createAddress(@RequestBody Address address) {
		this.addressRepo.save(address);
		return "New address created";
	}

	// update user
	@PutMapping("/modify/{addressId}")
	public Address updateAddress(@RequestBody Address address, @PathVariable ("addressId") String addressId) {
	
		Address existingAddress = this.addressRepo.findByAddressId(addressId).orElse(null);
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
		return this.addressRepo.save(existingAddress);
	}

	// delete user by id
	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<Address> deleteAddress(@PathVariable ("addressId") String addressId){
		Address existingAddress = this.addressRepo.findByAddressId(addressId).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
		}
		this.addressRepo.delete(existingAddress);
		return ResponseEntity.ok().build();
	}
}
