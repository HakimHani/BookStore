package com.springboot.bookshop.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.entity.Sales;





@Repository
public interface SalesRepository  extends JpaRepository<Sales, Long>{
	Optional<Sales> findById(Long id);
	Optional<Sales> findBySalesId(String salesId);
	Optional<Sales> findByCheckoutId(String checkoutId);
	List<Sales> findAllByCustomerEmail(String customerEmail);
	List<Sales> findAllByItemId(String itemId);
}
