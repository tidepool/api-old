package com.tidepool.api.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;

public class SpringAuthenticator implements UserDetailsService {

	private HBaseManager hBaseManager;
	
	@Autowired
	public void setHBaseManager(HBaseManager hBaseManager) {
		this.hBaseManager = hBaseManager;
	}
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		Account account = hBaseManager.getAccountFromEmail(email);
		return account;
	}

}
