package com.tidepool.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.CodedItem;

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
	
	@RequestMapping(value="/json/session.ajax", method=RequestMethod.POST)
	public @ResponseBody List<CodedItem> getSession(
			 @RequestParam(required=true) String machineId,
			 @RequestParam(required=true) String auth) {
				
		List<CodedItem> itemList = new ArrayList<CodedItem>();
				
		return itemList;
	}
	
	
	@RequestMapping(value="/json/items.ajax", method=RequestMethod.POST)
	public @ResponseBody List<CodedItem> getItems(
			 @RequestParam(required=true) String sessionId) {
				
		List<CodedItem> itemList = new ArrayList<CodedItem>();
				
		return itemList;
	}
	
	@RequestMapping(value="/json/item.ajax", method=RequestMethod.POST)
	public @ResponseBody CodedItem getItem(
			 @RequestParam(required=true) String sessionId) {
				
		CodedItem item = new CodedItem();
				
		return item;
	}
	

	@RequestMapping(value="/json/test.ajax", method=RequestMethod.POST)
	public @ResponseBody CodedItem test(
			HttpServletRequest request,
			@RequestParam(required=true) String id) {
			
			CodedItem item = new CodedItem();
			item.id = "TEST";			
			return item;
	}
			
	
	private Account getAccount() {
		Account account =  accountService.getAccount();		
		return account;
	}
	
	
	
}