package com.tidepool.api.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.tidepool.api.model.Account;

public class AccountService {
		
	
	public Account getAccount() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();    	
			if (principal instanceof UserDetails) {
				return ((Account)principal);
			} else {
				return null;
			}
		} 
		return null;
	}
	
}
