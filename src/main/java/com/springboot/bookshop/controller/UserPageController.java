package com.springboot.bookshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.bookshop.constant.enums.CheckoutState;
import com.springboot.bookshop.entity.Account;
import com.springboot.bookshop.entity.Address;
import com.springboot.bookshop.entity.Billing;
import com.springboot.bookshop.entity.Checkout;
import com.springboot.bookshop.entity.ItemInfo;
import com.springboot.bookshop.model.Visitor;
import com.springboot.bookshop.service.AccountService;
import com.springboot.bookshop.service.AddressService;
import com.springboot.bookshop.service.BillingService;
import com.springboot.bookshop.service.CheckoutService;
import com.springboot.bookshop.service.ItemInfoService;



@Controller
@RequestMapping("/profile")
@Scope("session")
public class UserPageController {

	@Autowired
	private AddressService addressService;


	@Autowired 
	private ItemInfoService itemInfoService;

	@Autowired
	private BillingService billingService;

	@Autowired
	private CheckoutService checkoutService;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private Visitor visitor;


	@GetMapping
	public String dashboradMain(Model model) {
		if(visitor == null) {
			System.out.println("user profile page: new visitor");
			visitor = new Visitor();
			return "redirect:/index";
		}else if(visitor.getUser() == null) {
			System.out.println("user profile page: no user");
			return "redirect:/index";
		}
		model.addAttribute("user",this.visitor.getUser());
		model.addAttribute("visitor",this.visitor);
		/*
		List<Address> address = addressRepo.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("addressList",address);
		List<Checkout> items = checkoutRepository.findAllByEmail(this.visitor.getUser().getEmail());
		items.get(0).getItems();
		model.addAttribute("purchasedItems",items);
		 */
		return "user-dasheboard";
	}
	
	@GetMapping("/account")
	public String accountEdit(Model model) {
		if(visitor == null) {
			System.out.println("user profile page: new visitor");
			visitor = new Visitor();
			return "redirect:/index";
		}else if(visitor.getUser() == null) {
			System.out.println("user profile page: no user");
			return "redirect:/index";
		}
		Account customerAccount = accountService.findByEmail(this.visitor.getUser().getEmail()).orElse(null);
		model.addAttribute("user",this.visitor.getUser());
		model.addAttribute("visitor",this.visitor);
		model.addAttribute("account",customerAccount);
		/*
		List<Address> address = addressRepo.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("addressList",address);
		List<Checkout> items = checkoutRepository.findAllByEmail(this.visitor.getUser().getEmail());
		items.get(0).getItems();
		model.addAttribute("purchasedItems",items);
		 */
		return "user-dashboard-account";
	}


	@GetMapping("/address")
	public String addressEdit(Model model) {
		if(visitor == null) {
			System.out.println("user profile page: new visitor");
			visitor = new Visitor();
			return "redirect:/index";
		}else if(visitor.getUser() == null) {
			System.out.println("user profile page: no user");
			return "redirect:/index";
		}
		model.addAttribute("user",this.visitor.getUser());
		List<Address> savedAddr = addressService.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("addresses",savedAddr);
		model.addAttribute("visitor",this.visitor);
		/*
		List<Address> address = addressRepo.findAllByEmail(this.visitor.getUser().getEmail());
		model.addAttribute("userInfo",this.visitor.getUser());
		model.addAttribute("addressList",address);
		List<Checkout> items = checkoutRepository.findAllByEmail(this.visitor.getUser().getEmail());
		items.get(0).getItems();
		model.addAttribute("purchasedItems",items);
		 */
		return "user-dashboard-address";
	}

	@GetMapping("/order")
	public String checkout(Model model) {
		if(visitor == null) {
			System.out.println("user profile page: new visitor");
			visitor = new Visitor();
			return "redirect:/index";
		}else if(visitor.getUser() == null) {
			System.out.println("user profile page: no user");
			return "redirect:/index";
		}
		model.addAttribute("user",this.visitor.getUser());
		List<Checkout> savedCheckouts = checkoutService.findAllByEmailAndCheckoutState(this.visitor.getUser().getEmail(),CheckoutState.PROCESSING_BILLING);
		
		HashMap<String,ItemInfo> hashItems = new HashMap<String,ItemInfo>();
		List<ItemInfo> itemsGroup = itemInfoService.findAll();
		for(ItemInfo item : itemsGroup) {
			hashItems.put(item.getProductId(), item);
		}
		

		HashMap<String,Address> hashAddr = new HashMap<String,Address>();
		HashMap<String,Billing> hashBill = new HashMap<String,Billing>();
		

		for(Checkout checkout : savedCheckouts) {
			checkout.setCheckoutState(CheckoutState.COMPLETED);
			Address savedAddress;
			Billing savedBilling;

			if(hashAddr.containsKey(checkout.getAddressId())) {
				savedAddress = hashAddr.get(checkout.getAddressId());
				System.out.println("Using address from hash");
			}else {
				savedAddress = addressService.findByAddressId(checkout.getAddressId()).orElse(null);
				if(savedAddress != null) {
					hashAddr.put(savedAddress.getAddressId(), savedAddress);
				}
			}

			if(hashBill.containsKey(checkout.getBillingId())) {
				savedBilling = hashBill.get(checkout.getBillingId());
				System.out.println("Using billing from hash");
			}else {
				savedBilling = billingService.findByBillingId(checkout.getBillingId()).orElse(null);
				if(savedBilling != null) {
					hashBill.put(savedBilling.getBillingId(), savedBilling);
				}
			}
			
			if(savedBilling != null) {
				savedBilling.setCardNumber(savedBilling.getCardNumber().substring(savedBilling.getCardNumber().length()-5,savedBilling.getCardNumber().length()-1));
			}

			checkout.setAddressDetail(savedAddress);
			checkout.setBillingDetail(savedBilling);
			List<String> ids = Arrays.asList(checkout.getItems().replace(" ", "").replace("[", "").replace("]", "").split(","));
			List<ItemInfo> items = new ArrayList<ItemInfo>();
			for(String id : ids) {
				System.out.println("Checking order item by item id " + id);
				if(hashItems.containsKey(id)) {
					items.add(hashItems.get(id));
					System.out.println("Using items from hash");
				}else {
					ItemInfo item = itemInfoService.findByProductId(id).orElse(null);
					if(item !=null) {
						hashItems.put(id, item);
						items.add(item);
					}else {
						items.add(new ItemInfo());
					}
				}

			}
			checkout.setItemsDetail(items);

		}


		model.addAttribute("checkouts",savedCheckouts);
		model.addAttribute("visitor",this.visitor);
		return "user-dashboard-checkout";
	}






}
