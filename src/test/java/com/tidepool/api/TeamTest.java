package com.tidepool.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.Team;
import com.tidepool.api.model.TeamAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/hbase-properties.xml"})

public class TeamTest {

	@Autowired
	private HBaseManager hBaseManager;
	
	
	@Test
	public void testCreateTeam() {
		
		assertNotNull(hBaseManager);
		
		Team team = new Team();
		team.setOwnerId("test");
		team.setName("Test Team");
		
		hBaseManager.saveTeam(team);
		Account account = new Account();
		account.setUserId("test");
		assertTrue(hBaseManager.getTeamsForAccount(account).size() > 0);
		
		Account newAccount = new Account();
		newAccount.setUserId("new_user");
		TeamAccount teamAccount = new TeamAccount();
		teamAccount.setAccount(newAccount);
		hBaseManager.addAccountToTeam(teamAccount, team);
		
		List<Team> teams = hBaseManager.getTeamsForAccount(account);
		assertTrue(teams.size() > 0);
		assertTrue(teams.get(0).getTeamMembers().size() == 0);
		Team lazyLoadTeam = teams.get(0);
		hBaseManager.loadAccountsForTeam(lazyLoadTeam);
		assertTrue(lazyLoadTeam.getTeamMembers().size() == 1);
		
		TeamAccount savedTeamAccount = lazyLoadTeam.getTeamMembers().get(0);
		assertTrue(!savedTeamAccount.isActive());
		
		savedTeamAccount.setActive(true);
		hBaseManager.saveTeam(lazyLoadTeam);
		
		teams = hBaseManager.getTeamsForAccount(account);
		lazyLoadTeam = teams.get(0);
		hBaseManager.loadAccountsForTeam(lazyLoadTeam);
		assertTrue(lazyLoadTeam.getTeamMembers().size() == 1);
		
		savedTeamAccount = lazyLoadTeam.getTeamMembers().get(0);
		assertTrue(savedTeamAccount.isActive());
		
	}
	
}
