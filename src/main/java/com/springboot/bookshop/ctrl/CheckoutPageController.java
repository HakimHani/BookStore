package com.springboot.bookshop.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.bookshop.Address;
import com.springboot.bookshop.IdentificationGenerator;
import com.springboot.bookshop.Visitor;
import com.springboot.bookshop.repo.AddressRepository;
import com.springboot.bookshop.repo.CheckoutRepository;
import com.springboot.bookshop.repo.ItemInfoRepository;



@Controller
@RequestMapping("/checkout")
@Scope("session")
public class CheckoutPageController {

	@Autowired
	private CheckoutRepository checkoutRepo;
	
	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private IdentificationGenerator idGenerator;

	@Autowired
	private ItemInfoRepository itemInfoRepository;
	

	@Autowired
	private Visitor visitor;
	
	
	@GetMapping()
	//@ResponseBody
	public String initialPage(Model model) {

		if(this.visitor.getCart().getItems().size() == 0) {
			System.out.println("Cart page: no user");
			//return "No items in cart";
			return "redirect:/cart";
		}

		return "redirect:/checkout/shipping";
	}
	
	@GetMapping("/shipping")
	//@ResponseBody
	public String shippingPage(Model model) {
		if(visitor == null) {
			System.out.println("checkout shipping page: new visitor");
			visitor = new Visitor();
			return "redirect:/index";
		}else if(visitor.getUser() == null) {
			System.out.println("checkout shipping page: no user");
			return "redirect:/index";
		}
		List<Address> address = addressRepo.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("addressList",address);
		model.addAttribute("cart",this.visitor.getCart().getItems());
		return "checkout_shipping_page";
	}
	
	
}
