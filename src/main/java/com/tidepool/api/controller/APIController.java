package com.tidepool.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.FactorAnalysisManager;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.BigFive;
import com.tidepool.api.model.Bullet;
import com.tidepool.api.model.CodedItem;
import com.tidepool.api.model.CodingEvent;

@Controller
public class APIController {
	
	@Value("${tidepool.training.cdn.url}") 
	private String cdnUrl;
	
	private FactorAnalysisManager factorAnalysisManager;
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
	
	@Autowired
	public void setFactorAnalysisManager(FactorAnalysisManager factorAnalysisManager) {
		this.factorAnalysisManager = factorAnalysisManager;
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
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		model.addAttribute("assessCode", 0);
		model.addAttribute("account", new Account());
		return "assessment/assessment-register";
	}
	
	/*
	 * 
	 * Assessment code.
	 * 
	 * 
	 */

	@RequestMapping(value="/assess1", method=RequestMethod.GET)
	public String getAssessmentRegister1(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", new Account());
		model.addAttribute("assessCode", 1);
		return "assessment/assessment-register";
	}
	
	
	@RequestMapping(value="/assess{code}", method=RequestMethod.GET)
	public String getAssessmentRegisterN(HttpServletRequest request, @PathVariable String code, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", new Account());
		model.addAttribute("assessCode", code);
		return "assessment/assessment-register";
	}
	
	
	@RequestMapping(value="/assessPost", method=RequestMethod.GET)
	public String postAssessmentRegister(HttpServletRequest request, 
			@RequestParam(required=false) String assessCode,
			@RequestParam(required=false) String zipCode,
			@RequestParam(required=false) String birthDate,
			@RequestParam(required=false) String gender,
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
				account.setAssessCode(assessCode);
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
				account.setIp(request.getRemoteAddr());
				account.setBirthYear(birthDate);
				account.setGender(gender);
				account = hBaseManager.createAssessAccount(account);
				request.getSession().setAttribute("account", account);
				
				CodingEvent event = new CodingEvent();
				event.user_id = account.getUserId();
				event.type = "start";	
				hBaseManager.logCodingEvent(event);	
				
			} catch (Exception e) {
				System.out.println("Error creating account: ");
				e.printStackTrace();	
			}
			
		
		return "forward:timing";
	}
	
	@RequestMapping(value="/assessment", method=RequestMethod.GET)
	public String getAssessment(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/assessment";
	}
	
	
	@RequestMapping(value="/image{imageNumber}", method=RequestMethod.GET)
	public String getImage(HttpServletRequest request,
			@PathVariable Integer imageNumber,
			@RequestParam(required=false) String owner, Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		int startIndex = 0;
		int increment = 5;
		if (imageNumber != null) {
			startIndex = imageNumber;
		}
		
		List<CodedItem> itemList = hBaseManager.getFolderCodedItems("work_type");
		if (itemList.size() > 0) {
			model.addAttribute("images", itemList.subList((startIndex * increment), ((startIndex * increment) + increment)));
		}
		model.addAttribute("cdn_url", cdnUrl);
		model.addAttribute("account", request.getSession().getAttribute("account"));
		
		if (startIndex == 5) {
			model.addAttribute("nextWindow", "assessmentFeedback");				
		} else {		
			model.addAttribute("nextWindow", "drag" + startIndex);				
		}
		return "assessment/image";
	}
	
	
	@RequestMapping(value="/drag0", method=RequestMethod.GET)
	public String getDrag0(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/drag0";
	}
	
	@RequestMapping(value="/drag1", method=RequestMethod.GET)
	public String getDrag1(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/drag1";
	}
	
	@RequestMapping(value="/drag2", method=RequestMethod.GET)
	public String getDrag2(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/drag2";
	}
	
	@RequestMapping(value="/drag3", method=RequestMethod.GET)
	public String getDrag3(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/drag3";
	}	

	@RequestMapping(value="/drag4", method=RequestMethod.GET)
	public String getDrag4(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/drag4";
	}		
	
	@RequestMapping(value="/resize", method=RequestMethod.GET)
	public String getResize(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/resize";
	}		
	
	
	@RequestMapping(value="/timing", method=RequestMethod.GET)
	public String getTiming(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("cdn_url", cdnUrl);	
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/timing";
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
			@RequestParam(required=false) String coordinates,
			@RequestParam(required=false) String x0,
			@RequestParam(required=false) String y0,
			@RequestParam(required=false) String x1,
			@RequestParam(required=false) String y1,
			@RequestParam(required=false) String width,
			@RequestParam(required=false) String height,
			@RequestParam(required=false) String startTime,
			@RequestParam(required=false) String endTime,
			@RequestParam(required=false) String screenWidth,
			@RequestParam(required=false) String screenHeight
			) {		

		if (!StringUtils.isEmpty(coordinates)) {
			String[] coordinatesArray = coordinates.split(",");
			for (String coordinateSubArray : coordinatesArray) {
				String[] coordinate = coordinateSubArray.split("-");
				if (coordinate.length == 2) {
					CodingEvent event = new CodingEvent();
					event.user_id = accountId;
					event.picture_id = explicitId;
					event.element = attributeId;
					event.type = type;
					event.x0 = coordinate[0];
					event.y0 = coordinate[1];					
					event.width = width;
					event.height = height;
					event.text = attributeComment;
					
					event.startTime = startTime;
					event.endTime = endTime;
					event.setIp(request.getRemoteHost());
					event.setResolution(request.getHeader("UA-resolution"));
					event.setScreenHeight(screenHeight);
					event.setScreenWidth(screenWidth);
					event.setUserAgent(request.getHeader("User-Agent"));
					hBaseManager.logCodingEvent(event);	
				}
			}
			return null;
		} else {

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
			event.startTime = startTime;
			event.endTime = endTime;
			event.setIp(request.getRemoteHost());
			event.setResolution(request.getHeader("UA-resolution"));
			event.setScreenHeight(screenHeight);
			event.setScreenWidth(screenWidth);			
			event.setUserAgent(request.getHeader("User-Agent"));
			hBaseManager.logCodingEvent(event);			
			return event;
		}
	}
	
	
	@RequestMapping(value="/assessmentFeedback", method=RequestMethod.GET)
	public String getAssessmentFeedback(HttpServletRequest request, @RequestParam(required=false) String owner, Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", request.getSession().getAttribute("account"));
		return "assessment/assessment-feedback";
	}
	
	@RequestMapping(value="/postAssessfeedback", method=RequestMethod.GET)
	public String postAssessmentFeedback(HttpServletRequest request, 
			@RequestParam(required=false) int artistic_appeal,
			@RequestParam(required=false) int effective_user_interface,
			@RequestParam(required=false) int interest_in_measurement,
			@RequestParam(required=false) int understanding_personality,
			@RequestParam(required=false) int interesting_dating_partners,						
			Model model) {
						
			try {				
				Account account = (Account)request.getSession().getAttribute("account");
				account.setArtistic_appeal(artistic_appeal);
				account.setEffective_user_interface(effective_user_interface);
				account.setInterest_in_measurement(interest_in_measurement);
				account.setUnderstanding_personality(understanding_personality);
				account.setInteresting_dating_partners(interesting_dating_partners);									
				hBaseManager.saveAccount(account);
				CodingEvent event = new CodingEvent();
				event.user_id = account.getUserId();
				event.type = "end";	
				hBaseManager.logCodingEvent(event);
				request.getSession().putValue("account", account);				
			} catch (Exception e) {				
				e.printStackTrace();
			}			
		
		return "assessment/assessment-thanks";
	}
	
	
	@RequestMapping(value="/json/bigfivebullet.ajax", method=RequestMethod.GET)
	public @ResponseBody List<Bullet> getBullet(HttpServletRequest request) {
		
		Account account = (Account)request.getSession().getAttribute("account");
		
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		
		long startTime = System.currentTimeMillis();
		BigFive bigFive = factorAnalysisManager.getTheBigFive("match_type", account);
		long duration = System.currentTimeMillis() - startTime;
		
		Bullet bullet0 = new Bullet();
		bullet0.setTitle("Openness");
		bullet0.setSubtitle("Measure of Openness");
		bullet0.setMarkers(Arrays.asList(200D));
		bullet0.setRanges(Arrays.asList(bigFive.getOpenness()));
		bullet0.setMeasures(Arrays.asList(50D));
		bullet0.setTiming(duration);
		
		Bullet bullet1 = new Bullet();
		bullet1.setTitle("Conscientiousness");
		bullet1.setSubtitle("Measure of Conscientiousness");
		bullet1.setMarkers(Arrays.asList(200D));
		bullet1.setRanges(Arrays.asList(bigFive.getConscientiousness()));
		bullet1.setMeasures(Arrays.asList(50D));
		
		Bullet bullet2 = new Bullet();
		bullet2.setTitle("Extraversion");
		bullet2.setSubtitle("Measure of Extraversion");
		bullet2.setMarkers(Arrays.asList(200D));
		bullet2.setRanges(Arrays.asList(bigFive.getExtraversion()));
		bullet2.setMeasures(Arrays.asList(50D));
		
		Bullet bullet3 = new Bullet();
		bullet3.setTitle("Agreeableness");
		bullet3.setSubtitle("Measure of Agreeableness");
		bullet3.setMarkers(Arrays.asList(200D));
		bullet3.setRanges(Arrays.asList(bigFive.getAgreeableness()));
		bullet3.setMeasures(Arrays.asList(50D));
		
		Bullet bullet4 = new Bullet();
		bullet4.setTitle("Neuroticism");
		bullet4.setSubtitle("Measure of Neuroticism");
		bullet4.setMarkers(Arrays.asList(200D));
		bullet4.setRanges(Arrays.asList(bigFive.getNeuroticism()));
		bullet4.setMeasures(Arrays.asList(50D));
				
		bullets.add(bullet0);
		bullets.add(bullet1);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullets.add(bullet4);
		return bullets;		
	}
	
	
	
	private Account getAccount() {
		Account account =  accountService.getAccount();		
		return account;
	}
	
	
	
}