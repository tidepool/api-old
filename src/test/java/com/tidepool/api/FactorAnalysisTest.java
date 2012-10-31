package com.tidepool.api;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidepool.api.data.FactorAnalysisManager;
import com.tidepool.api.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/hbase-properties.xml"})
public class FactorAnalysisTest {

	@Autowired
	private FactorAnalysisManager factorAnalysisManager;
		
	@Test
	public void testFactorAnalysisManager() {
		assertNotNull(factorAnalysisManager);
	}
	
	@Test
	public void testBig5() {
		Account account = new Account();
		account.setUserId("191");
		assertNotNull(factorAnalysisManager.getTheBigFive("match_type", account));
	}
	
}
