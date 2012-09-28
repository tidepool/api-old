package com.tidepool.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.tidepool.api.model.CodedItem;
import com.tidepool.api.model.CodingEvent;
import com.tidepool.api.model.CodingGroup;
import com.tidepool.api.model.TrainingItem;

@Controller
public class ImplicitCodingController {

	@Value("${tidepool.training.cdn.url}") 
	private String trainingCdnUrl;
	
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
				
		model.addAttribute("cdn_url", trainingCdnUrl);
		
		CodedItem codedItem = hBaseManager.getRandomCodedItem();
		model.addAttribute("codedItem", codedItem);
		
		
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
		
		TrainingItem trainingItem =  hBaseManager.getTrainingItem("11");
		
		for (String attributeName : attributeMap.keySet()) {
			
			if (trainingItem.getCodedItem().isAttributeActive(attributeName)) {
				allCodedAttributes.add(attributeMap.get(attributeName));
			}
		}
		
		
		ArrayList<CodedAttribute> attributeMapList = new ArrayList<CodedAttribute>(attributeMap.values());
		Random random = new Random();
		while (allCodedAttributes.size() < 6) {			
			CodedAttribute attribute = attributeMapList.get(random.nextInt(attributeMapList.size()));			
			boolean contains = false;
			for (CodedAttribute codedAttribute : allCodedAttributes) {
				if (codedAttribute.element.endsWith(attribute.element)) {
					contains = true;
					continue;
				}
			}
			if (!contains) {
				allCodedAttributes.add(attribute);
			}			
		}
		
		model.addAttribute("codedAttributes", allCodedAttributes);
				
		CodingGroup group = hBaseManager.getGroup(account.getElementGroupId());
		model.addAttribute("group", group);
		
		model.addAttribute("trainingItem", trainingItem);
		model.addAttribute("cdn_url", trainingCdnUrl);
			
		return "training/training0";
	}
	
	
	
	

	@RequestMapping(value="/training0Post", method=RequestMethod.POST)
	public String postTraining0(HttpServletRequest request, 
			@RequestParam(required=true) String button0, 
			@RequestParam(required=true) String button1, 
			@RequestParam(required=true) String button2,
			@RequestParam(required=true) String button3, 
			@RequestParam(required=true) String button4, 
			Model model) {		

		Account account = null;
		if ((account = accountService.getAccount()) != null) {
			model.addAttribute("account", accountService.getAccount());
		} else {
			return "redirect:/signin";
		}	

		Map parameterMap = request.getParameterMap();
		HashMap<String, CodedAttribute> codedMap = new HashMap<String, CodedAttribute>();			
		TrainingItem trainingItem =  hBaseManager.getTrainingItem("11");
		for(String attribute : attributeMap.keySet()) {
			if (trainingItem.getCodedItem().isAttributeActive(attribute)) {
				codedMap.put(attribute, attributeMap.get(attribute));
			}
		}

		int passCount = 0;
		boolean extraAnswer = false;
		Iterator keyIterator = parameterMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			String paramKey = (String)keyIterator.next();
			String parameterValue = request.getParameter(paramKey);
			if (!StringUtils.isEmpty(parameterValue) ) {
				if (codedMap.containsKey(parameterValue)) {
					passCount++;
				} else {
					extraAnswer = true;
				}				
			}
		}


		if (passCount == codedMap.size() && !extraAnswer) {
			account.setRegistrationLevel(REGISTRATION_LIMIT);
			hBaseManager.saveAccount(account);
			return "redirect:/test";
		}

		return "redirect:/training0";
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