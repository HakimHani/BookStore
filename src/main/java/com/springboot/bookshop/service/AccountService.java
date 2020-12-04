package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bookshop.entity.Account;
import com.springboot.bookshop.repo.AccountRepository;


@Service
public class AccountService {
	private final AccountRepository accountRepository;


	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}


	public List<Account> findAll(){
		return this.accountRepository.findAll();
	}

	public Optional<Account> findById(Long id){
		return this.accountRepository.findById(id);
	}

	public Optional<Account> findByEmail(String email){
		return this.accountRepository.findByEmail(email);
	}

	public Account save(Account account){
		return this.accountRepository.save(account);
	}
	
	public void delete(Account account){
		this.accountRepository.delete(account);
	}


}
