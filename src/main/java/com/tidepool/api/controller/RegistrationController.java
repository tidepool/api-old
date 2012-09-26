package com.tidepool.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;

@Controller
public class RegistrationController {

	private HBaseManager hBaseManager;
	private AccountService accountService;
	
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Autowired
	public void setHBaseManager(HBaseManager hBaseManager) {
		this.hBaseManager = hBaseManager;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String getPhotoList(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null) {
			return "signin/signin";
		} else {
			model.addAttribute("account", account);
		}

		return "training/registration";
	}
	
    
	@RequestMapping(value="/registerPost", method=RequestMethod.POST)
	public String registerPost(HttpServletRequest request,
			@RequestParam(required=true) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String gender,
			@RequestParam(required=false) String education,
			@RequestParam(required=false) String zipcode,
			@RequestParam(required=false) String birthYear) {
		Account account =  accountService.getAccount();				
		if (account == null) {
			return "signin/signin";
		}
		
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setGender(gender);
		account.setEducation(education);
		account.setZipCode(zipcode);
		account.setBirthYear(birthYear);		
		account.setRegistrationLevel(1);
		
		hBaseManager.saveAccount(account);
		
		return "redirect:/training0";
		
	}
	
}
