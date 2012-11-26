package com.tidepool.api;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.tidepool.api.email.EmailController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/email-properties.xml"})

public class EmailControllerTest {

	@Autowired
	public EmailController emailController;
	
	@Autowired
	public VelocityEngine velocityEngine;
	
	@Test
	public void testEmail() {
		
		assertNotNull(emailController);
		
		try {
			emailController.sendEmail("josephshoop@gmail.com", "admin@tidepool.co", "test", "testing the email, honey badger.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testVelocityEmail() {		
		Map model = new HashMap();
        model.put("testString", "pow");	
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/tidepool/api/email/test.vm", model);		
		try {
			emailController.sendEmail("josephshoop@gmail.com", "admin@tidepool.co", "test", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
