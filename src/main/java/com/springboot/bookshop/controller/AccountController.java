package com.springboot.bookshop.controller;

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

import com.springboot.bookshop.constant.enums.AccountType;
import com.springboot.bookshop.entity.Account;
import com.springboot.bookshop.entity.User;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.repo.AccountRepository;
import com.springboot.bookshop.repo.UserRepository;
import com.springboot.bookshop.service.AccountService;
import com.springboot.bookshop.service.UserService;



@Controller
@RequestMapping("/api/account")
@Scope("session")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private Visitor visitor;

	// get all users
	@GetMapping
	@ResponseBody
	public List<Account> getAllUsers() {
		return this.accountService.findAll();
	}

	// get user by id
	@GetMapping("/{email}")
	@ResponseBody
	public Account getUserByEmail(@PathVariable (value = "email") String cutomerEmail) {


		User user = this.userService.findByEmail(cutomerEmail).orElse(null);
		visitor.logIn(user);


		return this.accountService.findByEmail(cutomerEmail)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with email :" + cutomerEmail));
	}

	// create user
	@PostMapping("/create")
	@ResponseBody
	public String createUser(@RequestBody Account account) {

		if(this.accountService.findByEmail(account.getEmail()).orElse(null) != null) {
			System.out.println(this.accountService.findByEmail(account.getEmail()));
			return "Failed, Account already exist";
		}
		User user = this.userService.findByEmail(account.getEmail()).orElse(null);
		if(user != null) {
			return "Failed, user already exists";
		}
		account.setAccountType(AccountType.CUSTOMER);
		account.setRegisterDate("2020/12");
		this.accountService.save(account);
		user = new User(account.getEmail(),"User","N/A","N/A");
		this.userService.save(user);
		visitor.logIn(user);
		return "Success, New Account created";
	}
	
	// create user
	@PostMapping("/usercreate")
	@ResponseBody
	public ModelAndView userCreateUser(@RequestParam String email, @RequestParam String password, Model model) {

		if(this.accountService.findByEmail(email).orElse(null) != null) {
			System.out.println(this.accountService.findByEmail(email));
			//return "Failed, Account already exist";
			return new ModelAndView("redirect:/login?error=failed-dupacc");
		}
		User user = this.userService.findByEmail(email).orElse(null);
		if(user != null) {
			//return "Failed, user already exists";
			return new ModelAndView("redirect:/login?error=failed-dupuser");
		}
		Account account = new Account(email,password,AccountType.CUSTOMER,"2020/12");
		//account.setAccountType(AccountType.CUSTOMER);
		//account.setRegisterDate("2020/12");
		this.accountService.save(account);
		user = new User(email,"User","N/A","N/A");
		this.userService.save(user);
		visitor.logIn(user);
		return new ModelAndView("redirect:/profile");
		//return "Success, New Account created";
	}
	

	// login user
	@PostMapping("/login")
	@ResponseBody
	public String loginUser(@RequestBody Account account) {
		System.out.println("Logging in " + account.getEmail());
		System.out.println(account.getPassword());
		Account found = this.accountService.findByEmail(account.getEmail()).orElse(null);
		if( found != null) {
			if(account.getPassword().equals(found.getPassword())) {


				User user = this.userService.findByEmail(account.getEmail()).orElse(null);
				visitor.logIn(user);
				visitor.setPermission(found.getAccountType());

				return "Logged in";
			}else {
				return "Invalid password";
			}

		}
		return "Account not found";
	}


	@PostMapping("/userlogin")
	public ModelAndView userLogin(@RequestParam String email, @RequestParam String password, Model model) {
		
		System.out.println("Logging in " + email);
		System.out.println(password);
		
		Account found = this.accountService.findByEmail(email).orElse(null);
		if( found != null) {
			if(password.equals(found.getPassword())) {


				User user = this.userService.findByEmail(email).orElse(null);
				visitor.logIn(user);
				visitor.setPermission(found.getAccountType());

				return new ModelAndView("redirect:/?success");
			}else {
				return new ModelAndView("redirect:/login?wrongcredential=true");
			}

		}
		return new ModelAndView("redirect:/login?invaliduser");
	}

	@PostMapping("/logout")
	public ModelAndView lLogout(Model model) {
		if(this.visitor.getUser() != null) {
			this.visitor.logOut(visitor.getUser());
		}
		return new ModelAndView("redirect:/index?fresh");
	}
	
	@GetMapping("/userlogout")
	public ModelAndView userLogout(Model model) {
		if(this.visitor.getUser() != null) {
			this.visitor.logOut(visitor.getUser());
		}
		return new ModelAndView("redirect:/");
	}
	
	
	// update user
	@PutMapping("/update")
	@ResponseBody
	public String updateAccount(@RequestBody Account user) {
		Account existingUser = this.accountService.findByEmail(this.visitor.getUser().getEmail())
				.orElse(null);
		
		if(existingUser == null) {
			return "Failed";
		}
		
		if(!user.getOldPassword().equals(existingUser.getPassword())) {
			return "Failed, auth failed";
		}
		existingUser.setPassword(user.getPassword());
		this.accountService.save(existingUser);
		//existingUser.setRegisterDate(user.getRegisterDate());
		//return this.accountRepository.save(existingUser);
		return "Success";
	}

	// update user
	@PutMapping("/{id}")
	@ResponseBody
	public Account updateUser(@RequestBody Account user, @PathVariable ("id") long userId) {
		Account existingUser = this.accountService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		existingUser.setPassword(user.getPassword());
		existingUser.setRegisterDate(user.getRegisterDate());
		return this.accountService.save(existingUser);
	}

	// delete user by id
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Account> deleteUser(@PathVariable ("id") long userId){
		Account existingUser = this.accountService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		this.accountService.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
