package com.tidepool.api;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/hbase-properties.xml"})

public class AccountTest {

	@Autowired
	private HBaseManager hBaseManager;
	
	@Autowired
	private ShaPasswordEncoder encoder;
	
	//@Test
	public void testAccountLookup() {
		Account account = hBaseManager.getAccountFromEmail("jshoop@tidepool.co"); 		
		assertNotNull(account);
		assertTrue(account.getEmail().equals("jshoop@tidepool.co"));
		assertNotNull(account.getPassword());
		assertNotNull(account.getFirstName());
		assertNotNull(account.getLastName());
	}
	
	@Test
	public void testResetRegistration() {
		Account account = hBaseManager.getAccountFromEmail("jjingles@tidepool.com"); 		
		assertNotNull(account);
		assertTrue(account.getEmail().equals("jjingles@tidepool.com"));
		assertNotNull(account.getPassword());
		assertNotNull(account.getFirstName());
		assertNotNull(account.getLastName());
		
		account.setRegistrationLevel(3);
		account.setElementGroupId("1");
		hBaseManager.saveAccount(account);
		
	}
	
	
	//@Test
	public void testAccountSave() {
		
		Account account = hBaseManager.getAccountFromEmail("jshoop@tidepool.co"); 		
		assertNotNull(account);		
		account.setFirstName("Joseph");
		account.setRegistrationLevel(3);		
		account.setElementGroupId("0");		
		hBaseManager.saveAccount(account);		
		account = hBaseManager.getAccountFromEmail("jshoop@tidepool.co"); 		
		assertNotNull(account);
		assertTrue(account.getFirstName().equals("Joseph"));
		
	}
	
	
	//@Test
	public void testPassword() {
		
		String encoded = encoder.encodePassword("t1d3p00l", "jshoop@tidepool.co");
		System.out.println(encoded);
		assertTrue(encoder.isPasswordValid(encoded, "t1d3p00l", "jshoop@tidepool.co"));
		
	}
	
	
	
	//@Test
	public void testGroup() {
		
	}
	
	
	@Test
	public void testGetAccounts() {
		List<Account> accounts = hBaseManager.getAccounts(); 
		assertTrue(accounts.size() > 0);
	}
	
	
	//@Test
	public void testCreateAccount() {
		Account account = new Account();
		String email = "jshoop@tidepool.co";
		account.setEmail(email);
		account.setPassword(encoder.encodePassword("t1d3p00l", email));
		account.setElementGroupId("0");
		account.setFirstName("Joe");
		account.setLastName("Shoop");
		
		try {
		account = hBaseManager.createAccount(account);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: " + e.getMessage());
			fail();
		}
		assertNotNull(account);
		assertNotNull(account.getUserId());
		
		Account foundAccount = hBaseManager.getAccountFromEmail(email);
		assertNotNull(foundAccount);
		
	}
	
}