package com.springboot.bookshop.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.bookshop.Account;
import com.springboot.bookshop.User;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AccountRepository;
import com.springboot.bookshop.repo.UserRepository;




@Controller
@RequestMapping("/api/account")
@Scope("session")
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Visitor visitor;

	// get all users
	@GetMapping
	@ResponseBody
	public List<Account> getAllUsers() {
		return this.accountRepository.findAll();
	}

	// get user by id
	@GetMapping("/{email}")
	@ResponseBody
	public Account getUserByEmail(@PathVariable (value = "email") String cutomerEmail) {


		User user = this.userRepository.findByEmail(cutomerEmail).orElse(null);
		visitor.logIn(user);


		return this.accountRepository.findByEmail(cutomerEmail)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with email :" + cutomerEmail));
	}

	// create user
	@PostMapping("/create")
	@ResponseBody
	public String createUser(@RequestBody Account customer) {

		if(this.accountRepository.findByEmail(customer.getEmail()).orElse(null) != null) {
			System.out.println(this.accountRepository.findByEmail(customer.getEmail()));
			return "Account already exist";
		}
		this.accountRepository.save(customer);
		return "New Account created";
	}

	// login user
	@PostMapping("/login")
	@ResponseBody
	public String loginUser(@RequestBody Account account) {
		Account found = this.accountRepository.findByEmail(account.getEmail()).orElse(null);
		if( found != null) {
			if(account.getPassword().equals(found.getPassword())) {


				User user = this.userRepository.findByEmail(account.getEmail()).orElse(null);
				visitor.logIn(user);


				return "Logged in";
			}else {
				return "Invalid password";
			}

		}
		return "Account not found";
	}


	@PostMapping("/userlogin")
	public ModelAndView userLogin(@RequestParam String email, @RequestParam String password, Model model) {
		Account found = this.accountRepository.findByEmail(email).orElse(null);
		if( found != null) {
			if(password.equals(found.getPassword())) {


				User user = this.userRepository.findByEmail(email).orElse(null);
				visitor.logIn(user);


				return new ModelAndView("redirect:/index?success");
			}else {
				return new ModelAndView("redirect:/index?wrongcredential");
			}

		}
		return new ModelAndView("redirect:/index?invaliduser");
	}

	@PostMapping("/logout")
	public ModelAndView userLogout(Model model) {


		if(this.visitor.getUser() != null) {
			this.visitor.logOut(visitor.getUser());
		}


		return new ModelAndView("redirect:/index?fresh");
	}

	// update user
	@PutMapping("/{id}")
	@ResponseBody
	public Account updateUser(@RequestBody Account user, @PathVariable ("id") long userId) {
		Account existingUser = this.accountRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		existingUser.setPassword(user.getPassword());
		existingUser.setRegisterDate(user.getRegisterDate());
		return this.accountRepository.save(existingUser);
	}

	// delete user by id
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Account> deleteUser(@PathVariable ("id") long userId){
		Account existingUser = this.accountRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		this.accountRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
