package com.tidepool.api.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
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
import com.tidepool.api.model.Highlight;
import com.tidepool.api.model.TrainingItem;

@Controller
public class AdminController {

	@Value("${tidepool.training.cdn.url}") 
	private String cdnUrl;
	
	private HBaseManager hBaseManager;
	private AccountService accountService;
	private ShaPasswordEncoder encoder;
	
	private HashMap<String, CodedAttribute> attributeMap = null;
	
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Autowired
	public void setHBaseManager(HBaseManager hBaseManager) {
		this.hBaseManager = hBaseManager;
	}
	
	@Autowired
	public void setShaPasswordEncoder(ShaPasswordEncoder encoder) {
		this.encoder = encoder;
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String getAdmin(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}
		model.addAttribute("account", account);

		return "admin/admin";
	}
	
	@RequestMapping(value="/admin/accounts", method=RequestMethod.GET)
	public String getAccounts(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		
		model.addAttribute("account", account);		
		model.addAttribute("accounts", hBaseManager.getAccounts());
		
		return "admin/accounts";
	}
	
	@RequestMapping(value="/admin/trainingSets", method=RequestMethod.GET)
	public String getTrainingSets(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		model.addAttribute("account", account);
		
		List<TrainingItem> trainingSets = hBaseManager.getTrainingSets();
		model.addAttribute("trainingSets", trainingSets);
		model.addAttribute("cdn_url", cdnUrl);
		return "admin/training-sets";
	}
	
	@RequestMapping(value="/admin/trainingEditor", method=RequestMethod.GET)
	public String getTrainingEditor(HttpServletRequest request,			
			@RequestParam(required=false) String trainingId,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		model.addAttribute("account", account);
		
		buildAttributeMap();
		
		TrainingItem trainingSet = hBaseManager.getTrainingItem(trainingId);
		
		//TODO: Only set attributes needed.
		List<CodedAttribute> attributes = new ArrayList<CodedAttribute>();
		for (String attributeName : attributeMap.keySet()) {			
			if (trainingSet.getCodedItem().isAttributeActive(attributeName)) {
				CodedAttribute attribute = attributeMap.get(attributeName).clone();			
				attribute.setActive(true);
				attributes.add(attribute);
			}			
		}
		
		model.addAttribute("allAttributes", attributes);
		model.addAttribute("trainingItem", trainingSet);
		model.addAttribute("cdn_url", cdnUrl);
		
		return "admin/training-editor";
	}
	
	
	
	@RequestMapping(value="/admin/json/savetraining.ajax", method=RequestMethod.POST)
	public @ResponseBody TrainingItem logTrainingEvent(
			HttpServletRequest request,
			@RequestParam(required=true) String trainingId,
			@RequestParam(required=false) String attributeId,
			@RequestParam(required=false) String attributeValue,			
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

		TrainingItem item = hBaseManager.getTrainingItem(trainingId);
		if (item != null) { 
			CodedItem codedItem = item.getCodedItem();
			Class<CodedItem> codedItemClass = CodedItem.class;
			if (!StringUtils.isEmpty(attributeValue)) {
				try {
					Field field = codedItemClass.getField(attributeId);
					field.set(codedItem, attributeValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (!StringUtils.isEmpty(x0)) {
				Highlight highlight = new Highlight();
				highlight.x0 = Double.valueOf(x0);
				highlight.y0 = Double.valueOf(y0);
				highlight.x1 = Double.valueOf(x1);
				highlight.y1 = Double.valueOf(y1);
				
				if (!StringUtils.isEmpty(width)) {
					highlight.width = Double.valueOf(width);
				}
				
				if (!StringUtils.isEmpty(height)) {
					highlight.height = Double.valueOf(height);
				}
				
				item.getCodedItem().getHighlightMap().put(attributeId, highlight);
			}

			hBaseManager.saveTrainingItem(item);
		}
		return item;
	}

	
	
	@RequestMapping(value="/admin/groups", method=RequestMethod.GET)
	public String getGroups(HttpServletRequest request, @RequestParam(required=false) String owner, Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		buildAttributeMap();
		model.addAttribute("account", account);
		model.addAttribute("attributeMap", attributeMap);		
		model.addAttribute("groups", hBaseManager.getCodedGroups());
		
		return "admin/groups";
	}
	
	
	@RequestMapping(value="/admin/groupsEditor", method=RequestMethod.GET)
	public String getGroupsEditor(HttpServletRequest request, 
			@RequestParam(required=false) String groupId, 
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		model.addAttribute("account", account);
		
		buildAttributeMap();
		
		model.addAttribute("group", hBaseManager.getGroup(groupId));
		
		model.addAttribute("attributeMap", attributeMap);
		model.addAttribute("allAttributes", attributeMap.values());
		
		return "admin/groups-editor";
	}
	
	
	@RequestMapping(value="/admin/createAccount", method=RequestMethod.GET)
	public String getCreateAccount(HttpServletRequest request, @RequestParam(required=false) String owner,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		model.addAttribute("account", account);
		
		return "admin/create-account";
	}
	
    
	@RequestMapping(value="/admin/createAccountPost", method=RequestMethod.POST)
	public String registerPost(HttpServletRequest request,
			@RequestParam(required=true) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String password,
			@RequestParam(required=false) String group,
			@RequestParam(required=false) String folder,
			@RequestParam(required=false) String registration
			) {
		
		Account account =  accountService.getAccount();				
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}

		Account newAccount = new Account();	
		newAccount.setFirstName(firstName);
		newAccount.setLastName(lastName);
		newAccount.setFirstName(firstName);
		newAccount.setLastName(lastName);
		newAccount.setEmail(email);
		newAccount.setElementGroupId(group);
		newAccount.setPassword(encoder.encodePassword(password, email));
		newAccount.setExplicitImageFolder(folder);
		newAccount.setRegistrationLevel(registration);
		newAccount.setAccountStatus("0");
		
		try {
			hBaseManager.createAccount(newAccount);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return "redirect:/admin/accounts";

	}
	
	@RequestMapping(value="/admin/graphicstest", method=RequestMethod.GET)
	public String getGraphicsTest(HttpServletRequest request,			
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		model.addAttribute("account", account);
		
		TrainingItem trainingSet = hBaseManager.getTrainingItem("1");
		
		model.addAttribute("trainingItem", trainingSet);
		model.addAttribute("cdn_url", cdnUrl);
		
		return "admin/graphicstest";
	}
	
	@RequestMapping(value="/admin/image-viewer", method=RequestMethod.GET)
	public String getImageViewer(HttpServletRequest request,			
			@RequestParam(required=false) String type,
			Model model) {

		Account account =  accountService.getAccount();	
		if (account == null || !account.isAdmin()) {
			return "signin/signin";
		}			
		model.addAttribute("account", account);
		
		List<CodedItem> items = hBaseManager.getFolderCodedItems(type);
		
		model.addAttribute("codedItems", items);
		model.addAttribute("cdn_url", cdnUrl);
		
		return "admin/image-bucket-viewer";
	}
	
	
	private void buildAttributeMap() {
		if (attributeMap == null) {
			attributeMap = hBaseManager.getCodedElementsMap();
		}
	}
	
	
	@RequestMapping(value="/admin/graph", method=RequestMethod.GET)
	public String getGraph(HttpServletRequest request, @RequestParam(required=false) String owner, Model model) {
			
		return "admin/graph";
	}
	
	@RequestMapping(value="/admin/graph.json", method=RequestMethod.GET)
	public String getGraphJSON(HttpServletRequest request, @RequestParam(required=false) String owner, Model model) {			
		
		return "admin/graph_json";
	}
	
	
}
