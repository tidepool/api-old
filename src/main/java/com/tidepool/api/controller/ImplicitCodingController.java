package com.tidepool.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.tidepool.api.model.CodedAttribute;
import com.tidepool.api.model.CodingEvent;

@Controller
public class ImplicitCodingController {

	@Value("${tidepool.cdn.url}") 
	private String cdnUrl;
	
	@Value("${tidepool.attribute.page.limit}") 
	public int PAGE_LIMIT;
	
	@Value("${tidepool.registration.limit}") 
	private int REGISTRATION_LIMIT;
	
	private HBaseManager hBaseManager;
	private AccountService accountService;

	private HashMap<String, CodedAttribute> attributeMap = null;
	
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Autowired
	public void setHBaseManager(HBaseManager hBaseManager) {
		this.hBaseManager = hBaseManager;
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String getTestPage(HttpServletRequest request, 
								@RequestParam(required=false) String owner, 
								@RequestParam(required=false) Integer currentPage, 
								Model model) {			
		
		Account account = null;
		if ((account = accountService.getAccount()) != null) {
			model.addAttribute("account", accountService.getAccount());
		} else {
			return "redirect:/signin";
		}
		
		if (!account.isAdmin() && account.getRegistrationLevel() < REGISTRATION_LIMIT) {
			if (account.getRegistrationLevel() == 0) {
				return "redirect:/register";
			}
			return "redirect:/training" + (account.getRegistrationLevel() - 1);
		}
		
		
		buildAttributeMap();
				
		model.addAttribute("cdn_url", cdnUrl);
		
		//TODO: Get the photo:
		model.addAttribute("explicitId", "10001");
				
		//TODO: Get the attributes allowed.
		List<CodedAttribute> allCodedAttributes = new ArrayList<CodedAttribute>();
		
		List<List<CodedAttribute>> rollupList = new ArrayList<List<CodedAttribute>>();
		int subListSize = 0;
		int listSize = 0;
		List<CodedAttribute> subList = new ArrayList<CodedAttribute>();
		
		//TODO: Change to actual groups.
		for (CodedAttribute attribute : attributeMap.values()) {
			if ((subList.size() != 0) && (subList.size() % PAGE_LIMIT == 0) || (listSize == allCodedAttributes.size() - 1)) {
				subListSize = 0;
				rollupList.add(subList);
				subList = new ArrayList<CodedAttribute>();
			}
			subList.add(attribute);
			subListSize++;
			listSize++;
		}
		model.addAttribute("codedAttributes", rollupList);
		
		
		return "implicit/implicit-test-page";
	}

	private void buildAttributeMap() {
		if (attributeMap == null) {
			attributeMap = hBaseManager.getCodedElementsMap();
		}
	}
	
	@RequestMapping(value="/json/saveattribute.ajax", method=RequestMethod.POST)
	public @ResponseBody CodingEvent logEvent(
			HttpServletRequest request,
			@RequestParam(required=true) String explicitId,
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
		
		Account account = null;
		if ((account = accountService.getAccount()) == null) 
			return null;
			
			CodingEvent event = new CodingEvent();
			event.user_id = account.getUserId();
			event.picture_id = explicitId;
			event.element = attributeId;
			event.element_value = attributeValue;
			event.x0 = x0;
			event.y0 = y0;
			event.x1 = x1;
			event.y1 = y1;
			event.width = width;
			event.height = height;
			event.text = attributeComment;
			
			hBaseManager.logCodingEvent(event);			
			return event;
	}
	
	
	@RequestMapping(value="/training0", method=RequestMethod.GET)
	public String getTraining0(HttpServletRequest request, 
			@RequestParam(required=false) String owner, 
			@RequestParam(required=false) Integer currentPage, 
			Model model) {		

		Account account = null;
		if ((account = accountService.getAccount()) != null) {
			model.addAttribute("account", accountService.getAccount());
		} else {
			return "redirect:/signin";
		}
		
		buildAttributeMap();
		
		
		
		//TODO: Replace with actual training logic.
		
		List<CodedAttribute> allCodedAttributes = new ArrayList<CodedAttribute>();
		for (CodedAttribute attribute : attributeMap.values()) {
			allCodedAttributes.add(attribute);
		}
		
		model.addAttribute("codedAttributes", allCodedAttributes.subList(0, 5));
		
		return "training/training0";
	}
	
	@RequestMapping(value="/training0Post", method=RequestMethod.POST)
	public String postTraining0(HttpServletRequest request, 
			@RequestParam(required=true) String owner, 
			@RequestParam(required=true) Integer currentPage, 
			Model model) {		
		
		Account account = null;
		if ((account = accountService.getAccount()) != null) {
			model.addAttribute("account", accountService.getAccount());
		} else {
			return "redirect:/signin";
		}
		
		
		return "training/training1";
	}
	
	
	
	
	@RequestMapping(value="/training1", method=RequestMethod.GET)
	public String getTraining1(HttpServletRequest request, 
			@RequestParam(required=false) String owner, 
			@RequestParam(required=false) Integer currentPage, 
			Model model) {		
		
		return "training/training1";
	}
	
	
	
	
	@RequestMapping(value="/training1", method=RequestMethod.GET)
	public String getTraining2(HttpServletRequest request, 
			@RequestParam(required=false) String owner, 
			@RequestParam(required=false) Integer currentPage, 
			Model model) {		

		return "training/training3";
	}
	
}