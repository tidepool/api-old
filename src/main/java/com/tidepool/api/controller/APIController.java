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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.CodedItem;
import com.tidepool.api.model.CodingEvent;

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
				
		List<CodedItem> itemList = hBaseManager.getFolderCodedItems("work_type");
				
		return itemList;
	}
	
	@RequestMapping(value="/json/assessment.ajax", method=RequestMethod.POST)
	public @ResponseBody List<CodedItem> getAssessment(
			 @RequestParam(required=true) String sessionId) {
				
		List<CodedItem> itemList = hBaseManager.getFolderCodedItems("match_type");
				
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
	
	
	/*
	 * 
	 * Assessment code.
	 * 
	 * 
	 */
	
	
	@RequestMapping(value="/assess", method=RequestMethod.GET)
	public String getAssessmentRegister(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		model.addAttribute("account", new Account());
		return "assessment/assessment-register";
	}
	
	@RequestMapping(value="/assessPost", method=RequestMethod.GET)
	public String postAssessmentRegister(HttpServletRequest request, 
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String zipCode,
			@RequestParam(required=false) Integer extraverted,
			@RequestParam(required=false) Integer critical,
			@RequestParam(required=false) Integer dependable,
			@RequestParam(required=false) Integer anxious,
			@RequestParam(required=false) Integer open,
			@RequestParam(required=false) Integer reserved,
			@RequestParam(required=false) Integer sympathetic,
			@RequestParam(required=false) Integer disorganized,
			@RequestParam(required=false) Integer calm,
			@RequestParam(required=false) Integer conventional,			
			Model model) {
						
			try {
				
				Account account = new Account();
				account.setZipCode(zipCode);
				account.setExtraverted(extraverted);
				account.setCritical(critical);
				account.setDependable(dependable);
				account.setAnxious(anxious);
				account.setOpen(open);
				account.setReserved(reserved);
				account.setSympathetic(sympathetic);
				account.setDisorganized(disorganized);
				account.setCalm(calm);
				account.setConventional(conventional);
				
				
				account = hBaseManager.createAssessAccount(account);
				request.getSession().putValue("account", account);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		return "forward:assessment";
	}
	
	@RequestMapping(value="/assessment", method=RequestMethod.GET)
	public String getAssessment(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/assessment";
	}
	
	@RequestMapping(value="/json/assessmentevent.ajax", method=RequestMethod.POST)
	public @ResponseBody CodingEvent logTrainingEvent(
			HttpServletRequest request,
			@RequestParam(required=true) String accountId,
			@RequestParam(required=true) String explicitId,
			@RequestParam(required=true) String type,
			@RequestParam(required=false) String attributeId,
			@RequestParam(required=false) String attributeValue,
			@RequestParam(required=false) String attributeComment,
			@RequestParam(required=false) String x0,
			@RequestParam(required=false) String y0,
			@RequestParam(required=false) String x1,
			@RequestParam(required=false) String y1,
			@RequestParam(required=false) String width,
			@RequestParam(required=false) String height
			) {		
		
			CodingEvent event = new CodingEvent();
			event.user_id = accountId;
			event.picture_id = explicitId;
			event.element = attributeId;
			event.type = type;
			event.x0 = x0;
			event.y0 = y0;
			event.x1 = x1;
			event.y1 = y1;
			event.width = width;
			event.height = height;
			event.text = attributeComment;
			
			hBaseManager.logTrainingEvent(event);			
			return event;
	}
	
	
	@RequestMapping(value="/assessmentFeedback", method=RequestMethod.GET)
	public String getAssessmentFeedback(HttpServletRequest request, @RequestParam(required=false) String owner, Model model) {
		
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/assessment-feedback";
	}
	
	@RequestMapping(value="/postAssessfeedback", method=RequestMethod.GET)
	public String postAssessmentFeedback(HttpServletRequest request, 
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String zipCode,
			@RequestParam(required=false) Integer extraverted,
			@RequestParam(required=false) Integer critical,
			@RequestParam(required=false) Integer dependable,
			@RequestParam(required=false) Integer anxious,
			@RequestParam(required=false) Integer open,
			@RequestParam(required=false) Integer reserved,
			@RequestParam(required=false) Integer sympathetic,
			@RequestParam(required=false) Integer disorganized,
			@RequestParam(required=false) Integer calm,
			@RequestParam(required=false) Integer conventional,			
			Model model) {
						
			try {
				
				Account account = new Account();
				account.setZipCode(zipCode);
				account.setExtraverted(extraverted);
				account.setCritical(critical);
				account.setDependable(dependable);
				account.setAnxious(anxious);
				account.setOpen(open);
				account.setReserved(reserved);
				account.setSympathetic(sympathetic);
				account.setDisorganized(disorganized);
				account.setCalm(calm);
				account.setConventional(conventional);
				
				
				account = hBaseManager.createAssessAccount(account);
				request.getSession().putValue("account", account);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		return "forward:assessment-thanks";
	}
	
	
	private Account getAccount() {
		Account account =  accountService.getAccount();		
		return account;
	}
	
	
	
}