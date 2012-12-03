package com.tidepool.api;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Invite;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/hbase-properties.xml"})

public class InviteTest {

	@Autowired
	private HBaseManager hBaseManager;
	
	
	@Test
	public void testInvite() {
		Invite invite = hBaseManager.createInvite("Pig", "Bodine", 101L);
		
		assertNotNull(invite);
		
		System.out.println(invite.getSecret());
			
		Invite foundInvite = hBaseManager.lookupInvite(invite.getSecret());
		
		assertNotNull(foundInvite);
		
		
		
	}
	
}
