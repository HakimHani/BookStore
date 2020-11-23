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

import com.springboot.bookshop.User;
import com.springboot.bookshop.exception.ResourceNotFoundException;

import com.springboot.bookshop.repo.UserRepository;



@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// get all users
	@GetMapping
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	// get user by id
	@GetMapping("/{email}")
	public User getUserByEmail(@PathVariable (value = "email") String cutomerEmail) {
		return this.userRepository.findByEmail(cutomerEmail)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + cutomerEmail));
	}

	// create user
	@PostMapping
	public String createUser(@RequestBody User customer) {
		
		if(this.userRepository.findByEmail(customer.getEmail()).orElse(null) != null) {
			System.out.println(this.userRepository.findByEmail(customer.getEmail()));
			return "Customer already exist";
		}
		this.userRepository.save(customer);
		return "New customer created";
	}
	
	// update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable ("id") long userId) {
		User existingUser = this.userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 return this.userRepository.save(existingUser);
	}
	
	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
		User existingUser = this.userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		 this.userRepository.delete(existingUser);
		 return ResponseEntity.ok().build();
	}
}
