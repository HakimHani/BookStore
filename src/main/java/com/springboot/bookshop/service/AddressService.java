package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bookshop.entity.Account;
import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AccountRepository;
import com.springboot.bookshop.repo.AddressRepository;


@Service
public class AddressService {
	private final AddressRepository addressRepository;


	public AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}


	public List<Address> findAll(){
		return this.addressRepository.findAll();
	}

	public Optional<Address> findById(Long id){
		return this.addressRepository.findById(id);
	}

	public List<Address> findAllByEmail(String email){
		return this.addressRepository.findAllByEmail(email);
	}
	
	public Optional<Address> findByAddressId(String addressId){
		return this.addressRepository.findByAddressId(addressId);
	}

	public Address save(Address address){
		return this.addressRepository.save(address);
	}
	
	public void delete(Address address){
		this.addressRepository.delete(address);
	}
	
	public Boolean modify(Address address) {
		Address existingAddress = this.findByAddressId(address.getAddressId()).orElse(null);
		if(existingAddress == null) {
			System.out.println("Address not found with addressId :\" + addressId");
			return false;
			//throw new ResourceNotFoundException("Address not found with addressId :" + addressId);
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
		this.save(existingAddress);
		return true;
	}


}
