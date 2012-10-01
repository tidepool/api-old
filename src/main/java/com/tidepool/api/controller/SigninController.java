package com.tidepool.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.model.Account;

@Controller
public class SigninController {

	private AccountService accountService;
    
    @Autowired
    public void setAccountService(AccountService accountService) {
    	this.accountService = accountService;
    }
	
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String signin(WebRequest request, @CookieValue(value="u", defaultValue="xxxx", required=false) String cookie) {
		Account account =  accountService.getAccount();				
		if (account == null) {
			return "signin/signin";
		}
		return "redirect:/survey";
		
	}
	
}
