package com.tidepool.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.Photo;

@Controller
public class APIController {
	
	@Value("${tidepool.cdn.url}") 
	private String cdnUrl;
		
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
	
	@RequestMapping(value="/api/list", method=RequestMethod.GET)
	public String getPhotoList(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {

		Account account = null;
		if ((account = getAccount()) != null) {
			model.addAttribute("account", accountService.getAccount());
		} else {
			return "redirect:/signin";
		}

		List<Photo> photoList = hBaseManager.getAllPhotos();	
		model.addAttribute("photos",  photoList);
		model.addAttribute("cdn_url", cdnUrl);			
		return "api/photo-list";
	}


	private Account getAccount() {
		Account account =  accountService.getAccount();		
		return account;
	}
	
}