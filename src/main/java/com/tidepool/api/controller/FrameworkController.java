package com.tidepool.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.email.EmailController;
import com.tidepool.api.model.Account;

@Controller
public class FrameworkController {

	@Value("${tidepool.cdn.url}") 
	private String cdnUrl;
	
	
	private HBaseManager hBaseManager;
	private AccountService accountService;
	private ShaPasswordEncoder encoder;
	private AuthenticationManager authManager;
	private VelocityEngine velocityEngine;
	private EmailController emailController;
	
	@Autowired  
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@Autowired
	public void setEmailController(EmailController emailController) {
		this.emailController = emailController;
	}
	
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
	
	@Autowired 
	public void setAuthManager(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	
	@RequestMapping(value="/adminhome", method=RequestMethod.GET)
	public String getAdminHome(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", getAccount());
		
		return "framework/admin/home";
	}
	
	@RequestMapping(value="/team", method=RequestMethod.GET)
	public String getAdminTeam(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", getAccount());
		
		return "framework/admin/team";
	}
	
	@RequestMapping(value="/testtemplate", method=RequestMethod.GET)
	public String getTest(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", getAccount());
		
		return "framework/template/template";
	}
	
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String getAssessmentRegister(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		return "framework/admin/register";
	}
	
	
	@RequestMapping(value="/userRegister", method=RequestMethod.POST)
	public String registerPost(HttpServletRequest request,
			@RequestParam(required=true) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String phoneNumber,
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String password,
			@RequestParam(required=false) String company) {
		
		Account account = new Account();
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setPhoneNumber(phoneNumber);
		account.setEmail(email);
		account.setPassword(encoder.encodePassword(password, email));
		account.setCompany(company);
		account.setRegistrationLevel("1");
		account.setIp(request.getRemoteAddr());
		account.setAccountStatus("0");
		
		try {
			hBaseManager.createAccount(account);
		} catch (Exception exception) {			
			exception.printStackTrace();
		}
		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(account.getEmail(), password);
		Authentication result = authManager.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(result);
				
		return "redirect:/assess";
		
	}
	
	
	private Account getAccount() {
		Account account =  accountService.getAccount();		
		return account;
	}
		
	
}
