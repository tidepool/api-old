package com.tidepool.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.mail.Session;
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

import com.tidepool.api.authentication.AccountService;
import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.email.EmailController;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.Invite;
import com.tidepool.api.model.Team;
import com.tidepool.api.model.TeamAccount;

@Controller
public class FrameworkController {

	@Value("${tidepool.cdn.url}") 
	private String cdnUrl;
	
	@Value("${tidepool.invite.url}") 
	private String inviteUrl;
	
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
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String getHome(HttpServletRequest request,
			@RequestParam(required=false) String owner,
			Model model) {
		
		Account account =  accountService.getAccount();				
		if (account == null) {
			return "framework/admin/register";
		}
		
		else if (account.isFrameworkAdmin()) {
			return "forward:/adminhome";
		}
		
		else if (account.isFrameworkUser()) {
			return "forward:/userhome";
		}
		
		return "framework/admin/register";
		
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
	
	@RequestMapping(value="/userhome", method=RequestMethod.GET)
	public String getUserHome(HttpServletRequest request, 
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
		
		return "framework/user/home";
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
		
			if (team != null) {
				List<Invite> invites = hBaseManager.getInvitesForTeam(team);
				HashMap<String, Invite> inviteMap = new HashMap<String, Invite>();
				for (Invite invite : invites) {
					inviteMap.put(invite.getAccountId(), invite);
				}
				model.addAttribute("inviteMap", inviteMap);
			}
		}
		
		
		
		model.addAttribute("account", account);
		
		return "framework/admin/team";
	}
	
	
	@RequestMapping(value="/teamSavePost", method=RequestMethod.POST)
	public  @ResponseBody Team teamSavePost(HttpServletRequest request, 
			@RequestParam(required=true) String teamName,
			@RequestParam(required=false) Long teamId,
			@RequestParam(required=true) String timeline) {
		
		Account account =  getAccount();				
		if (account == null) {
			return null;
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
		
		return team;
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
		
		String[] activeUsers =  request.getParameterValues("activeUsers");		
		hBaseManager.loadAccountsForTeam(team);
		
		for (TeamAccount teamAccount : team.getTeamMembers()) {
			teamAccount.setActive(false);
			if (activeUsers != null) {
				for (String activeId : activeUsers) {				
					if (teamAccount.getAccount().getUserId().equals(activeId)) {					
						teamAccount.setActive(true);					
					}
				}
			}
			hBaseManager.saveTeamAccount(teamAccount);
		}
		
		for (TeamAccount teamAccount : team.getTeamMembers()) {
			if (teamAccount.isActive()) {
				Invite invite = hBaseManager.createInvite(account.getUserId(), teamAccount.getAccount().getUserId(), team.getId());				
				emailController.sendInvitationEmail(teamAccount.getAccount(), account, invite);
			}
		}
		
		return "redirect:teams";
	}
	
	@RequestMapping(value="/teamMemberPost", method=RequestMethod.POST)
	public @ResponseBody Account teamMemberPost(HttpServletRequest request,
			@RequestParam(required=true) Long teamId,
			@RequestParam(required=true) String firstName,
			@RequestParam(required=true) String lastName,			
			@RequestParam(required=true) String email,
			@RequestParam(required=true) String age,
			@RequestParam(required=true) String gender,			
			@RequestParam(required=true) String jobTitle) {
		
		Account account =  getAccount();				
		if (account == null) {
			return null;
		}
		
		Team team = hBaseManager.getTeamFromId(teamId);
		
		Account newAccount = hBaseManager.getAccountFromEmailInternal(email);
		
		if (newAccount == null) {
			newAccount = new Account();
			newAccount.setFirstName(firstName);
			newAccount.setLastName(lastName);
			newAccount.setEmail(email);
			newAccount.setJobTitle(jobTitle);
			newAccount.setAge(age);
			newAccount.setGender(gender);

			try {
				newAccount = hBaseManager.createAccount(newAccount);
			} catch (Exception e) {			
				e.printStackTrace();
			}
		}
		TeamAccount teamAccount = new TeamAccount();
		teamAccount.setActive(true);
		teamAccount.setAccount(newAccount);
		hBaseManager.addAccountToTeam(teamAccount, team);
		
	
		return newAccount;
	}
	
	
	@RequestMapping(value="/emailPost", method=RequestMethod.POST)
	public @ResponseBody Account emailPost(HttpServletRequest request,
			@RequestParam(required=true) Long teamId,
			@RequestParam(required=true) String emailSubject,
			@RequestParam(required=true) String emailBody,
			@RequestParam(required=true) String emailReminder) {
		
		Account account =  getAccount();				
		if (account == null) {
			return null;
		}
		
		Team team = hBaseManager.getTeamFromId(teamId);
		team.setInviteBody(emailBody);
		team.setInviteSubject(emailSubject);
		team.setInviteReminder(emailReminder);
		hBaseManager.saveTeam(team);
		
		return null;
	}
	
	
	@RequestMapping(value="/reports", method=RequestMethod.GET)
	public String getAdminReports(HttpServletRequest request, 
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
		model.addAttribute("reports", hBaseManager.getTeamsForAccount(account));
		
		return "framework/admin/reports";
	}
	
	@RequestMapping(value="/report{reportId}", method=RequestMethod.GET)
	public String getAdminReport(HttpServletRequest request,
			@PathVariable Long reportId,
			@RequestParam(required=false) String owner,
			Model model) {
		
		Account account =  getAccount();				
		if (account == null) {
			return "framework/admin/register";
		}
		
		if (account.isAdmin()) {
			model.addAttribute("admin", account);
		}
		
		
		
		model.addAttribute("account", account);
		
		return "framework/admin/report";
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
	
	@RequestMapping(value="/endUserRegister", method=RequestMethod.GET)
	public String getEndUserRegister(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			Model model) {
		
		if (getAccount() != null && getAccount().isAdmin()) {
			model.addAttribute("admin", getAccount());
		}
		
		Invite invite = (Invite) request.getSession().getAttribute("invite");		
		if (invite != null) {
			model.addAttribute("account", hBaseManager.getAccountFromId(invite.getAccountId()));						
		}
			
		return "framework/user/register";
	}
	
	
	@RequestMapping(value="/endUserRegisterPost", method=RequestMethod.POST)
	public String endUserRegisterPost(HttpServletRequest request,
			@RequestParam(required=true) String firstName,
			@RequestParam(required=false) String lastName,			
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String password
			) {
		
		Invite invite = (Invite) request.getSession().getAttribute("invite");
		Account newAccount = null;
		if (invite != null) {
			newAccount = hBaseManager.getAccountFromId(invite.getAccountId());			
			invite.setStatus(Invite.CLOSED_STATUS);	
			hBaseManager.saveInvite(invite);
		} else {		
			newAccount = new Account();
		}
		
		newAccount.setFirstName(firstName);
		newAccount.setLastName(lastName);		
		newAccount.setEmail(email);
		newAccount.setPassword(encoder.encodePassword(password, email));		
		newAccount.setRegistrationLevel("1");
		newAccount.setIp(request.getRemoteAddr());
		newAccount.setAccountStatus("0");
		newAccount.setElementGroupId(Account.FRAMEWORK_USER);
				
		try {
			
			if (invite != null) {
				hBaseManager.saveAccount(newAccount);
			} else {
				hBaseManager.createAccount(newAccount);
			}
		
		} catch (Exception exception) {			
			exception.printStackTrace();
		}
		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(newAccount.getEmail(), password);
		Authentication result = authManager.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(result);
				
		return "redirect:/home";
		
	}
	
	
	@RequestMapping(value="/userRegister", method=RequestMethod.POST)
	public String registerPost(HttpServletRequest request,
			@RequestParam(required=true) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String phoneNumber,
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String password,
			@RequestParam(required=false) String company) {
		
		Account newAccount = new Account();
		newAccount.setFirstName(firstName);
		newAccount.setLastName(lastName);
		newAccount.setPhoneNumber(phoneNumber);
		newAccount.setEmail(email);
		newAccount.setPassword(encoder.encodePassword(password, email));
		newAccount.setCompany(company);
		newAccount.setRegistrationLevel("1");
		newAccount.setIp(request.getRemoteAddr());
		newAccount.setAccountStatus("0");
		newAccount.setElementGroupId(Account.FRAMEWORK_ADMIN);
		
		try {
			hBaseManager.createAccount(newAccount);
		} catch (Exception exception) {			
			exception.printStackTrace();
		}
		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(newAccount.getEmail(), password);
		Authentication result = authManager.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(result);
				
		return "redirect:/home";
		
	}
	
	@RequestMapping(value="/invite/{inviteId}", method=RequestMethod.GET)
	public String getInvite(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			@PathVariable String inviteId,
			Model model) {
		
		Invite foundInvite = hBaseManager.lookupInvite(inviteId);
		
		if (foundInvite == null) {
			return "redirect:/register";
		}
		
		request.getSession().setAttribute("invite", foundInvite);		
		
		return "redirect:/assess10";
	}
	
	
	@RequestMapping(value="/passwordEmailPost", method=RequestMethod.POST)
	public @ResponseBody String registerPost(HttpServletRequest request,
			@RequestParam(required=true) String email) {
		
		Account account = hBaseManager.getAccountFromEmailInternal(email);
		if (account == null) {
			return "fail";
		}
		
		long now = System.currentTimeMillis();
		String encoded = encoder.encodePassword(account.getUserId(), now);
		account.setPasswordResetChallenge(encoded);
		account.setPasswordResetChallengeTimestamp(now);
		hBaseManager.saveAccount(account);
		
		emailController.sendForgotPasswordEmail(account);
		
		return null;
	}
	
	
	@RequestMapping(value="/resetpassword/{challenge}", method=RequestMethod.GET)
	public String resetPassword(HttpServletRequest request, 
			@RequestParam(required=false) String owner,
			@PathVariable String challenge,
			Model model) {
		
		model.addAttribute("challenge", challenge);	
		
		return "framework/admin/reset-password";
	}
	
	
	@RequestMapping(value="/resetpasswordPost", method=RequestMethod.POST)
	public String resetPasswordPost(HttpServletRequest request, 
			@RequestParam(required=false) String challenge,
			@RequestParam(required=false) String password,
			Model model) {
		
		Account account = hBaseManager.getAccountFromPasswordChallenge(challenge);
		
		if (account == null) {
			return "framework/admin/register";
		}
		
		account.setPassword(encoder.encodePassword(password, account.getEmail()));
		account.setPasswordResetChallenge(null);
		account.setPasswordResetChallengeTimestamp(0);
		hBaseManager.saveAccount(account);
		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(account.getEmail(), password);
		Authentication result = authManager.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(result);
		
		
		return "redirect:/home";
	}
	
	private Account getAccount() {
		Account account =  accountService.getAccount();		
		return account;
	}
		
	
}
