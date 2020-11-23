package com.springboot.bookshop.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.Address;




@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	List<Address> findAllByEmail(String email);
	Optional<Address> findByAddressId(String addressId);
}