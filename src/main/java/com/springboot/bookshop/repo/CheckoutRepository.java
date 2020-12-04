package com.springboot.bookshop.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.Checkout;





@Repository
public interface CheckoutRepository  extends JpaRepository<Checkout, Long>{
	Optional<Checkout> findById(Long id);
	Optional<Checkout> findByCheckoutId(String checkoutId);
	List<Checkout> findAllByEmail(String email);
	List<Checkout> findAllByEmailAndCheckoutState(String email,CheckoutState checkoutState);
}
