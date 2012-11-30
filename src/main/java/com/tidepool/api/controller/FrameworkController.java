package com.tidepool.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.scheduling.annotation.Scheduled;

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.email.EmailController;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.CodingEvent;
import com.tidepool.api.model.Team;
import com.tidepool.api.model.TeamAccount;

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
	
	private SimpleDateFormat dateFormat =new SimpleDateFormat("mm/dd/yyyy");
	
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
		
		Account account =  getAccount();				
		if (account == null) {
			return "framework/admin/register";
		}
		
		if (account != null && account.isAdmin()) {
			model.addAttribute("admin", account);
		}
		
		model.addAttribute("account", account);
		
		return "framework/admin/home";
	}
	
	@RequestMapping(value="/teams", method=RequestMethod.GET)
	public String getAdminTeams(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			Model model) {
		
		Account account =  getAccount();				
		if (account == null) {
			return "framework/admin/register";
		}
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		model.addAttribute("account", account);		
		model.addAttribute("teams", hBaseManager.getTeamsForAccount(account));
		
		return "framework/admin/teams";
	}
	
	@RequestMapping(value="/team{teamId}", method=RequestMethod.GET)
	public String getAdminTeam(HttpServletRequest request,
			@PathVariable Long teamId,
			@RequestParam(required=false) String owner,
			Model model) {
		
		Account account =  getAccount();				
		if (account == null) {
			return "framework/admin/register";
		}
		
		if (account.isAdmin()) {
			model.addAttribute("admin", account);
		}
		
		if (teamId != null) {
			Team team = hBaseManager.getTeamFromId(teamId);
			hBaseManager.loadAccountsForTeam(team);
			model.addAttribute("team", team);
		}
		
		model.addAttribute("account", account);
		
		return "framework/admin/team";
	}
	

	@RequestMapping(value="/teamPost", method=RequestMethod.POST)
	public String teamPost(HttpServletRequest request, 
			@RequestParam(required=true) String teamName,
			@RequestParam(required=false) Long teamId,
			@RequestParam(required=true) String timeline) {
		
		Account account =  getAccount();				
		if (account == null) {
			return "framework/admin/register";
		}
			
		Team team = null;
		if (teamId != null) {
			team = hBaseManager.getTeamFromId(teamId);
		} else {
			team = new Team();
		}
		team.setName(teamName);
		team.setOwnerId(account.getUserId());
		if (!StringUtils.isEmpty(timeline)) {
			try {
				long date = dateFormat.parse(timeline).getTime();
				team.setTimeline(date);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		if (teamId != null) {
			hBaseManager.saveTeam(team);
		} else {
			hBaseManager.createTeam(team);
		}
		
		return "redirect:teams";
	}
	
	@RequestMapping(value="/emailPost", method=RequestMethod.POST)
	public @ResponseBody Account teamMemberPost(HttpServletRequest request,
			@RequestParam(required=true) Long teamId,
			@RequestParam(required=true) String emailSubject,
			@RequestParam(required=true) String emailBody) {
		
		Account account =  getAccount();				
		if (account == null) {
			return null;
		}
		
		Team team = hBaseManager.getTeamFromId(teamId);
		team.setInviteBody(emailBody);
		team.setInviteSubject(emailSubject);
		hBaseManager.saveTeam(team);
		
		return null;
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
		account.setElementGroupId(Account.FRAMEWORK_ADMIN);
		
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
