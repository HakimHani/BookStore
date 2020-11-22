package com.springboot.bookshop.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.Address;
import com.springboot.bookshop.Billing;




@Repository
public interface BillingRepository  extends JpaRepository<Billing, Long> {
	Optional<Billing> findById(Long id);
	List<Billing> findAllByEmail(String email);
}
