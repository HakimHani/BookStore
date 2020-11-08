package com.springboot.bookshop.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.Account;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByEmail(String email);
}