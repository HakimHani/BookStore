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
import com.springboot.bookshop.Address;
import com.springboot.bookshop.User;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.exception.ResourceNotFoundException;
import com.springboot.bookshop.repo.AccountRepository;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.UserRepository;




@Controller
@RequestMapping("/profile")
@Scope("session")
public class UserPageController {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Visitor visitor;


	@GetMapping
	public String getUserProfilePage(Model model) {
		if(visitor == null) {
			System.out.println("user profile page: new visitor");
			visitor = new Visitor();
			return "redirect:/index";
		}else if(visitor.getUser() == null) {
			System.out.println("user profile page: no user");
			return "redirect:/index";
		}
		List<Address> address = addressRepo.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("addressList",address);
		return "profile";
	}

	// get user by id
	
}
