package com.springboot.bookshop.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;



import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.repo.UserRepository;


@Service
public class UserService {
	private final UserRepository userRepository;


	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	public List<User> findAll(){
		return this.userRepository.findAll();
	}

	public Optional<User> findById(Long id){
		return this.userRepository.findById(id);
	}
	
	public Optional<User> findByEmail(String email){
		return this.userRepository.findByEmail(email);
	}


	public User save(User user){
		return this.userRepository.save(user);
	}
	
	public void delete(User user){
		this.userRepository.delete(user);
	}


}
