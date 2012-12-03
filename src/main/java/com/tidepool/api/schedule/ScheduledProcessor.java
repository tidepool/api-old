package com.tidepool.api.schedule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.email.EmailController;
import com.tidepool.api.model.Team;
import com.tidepool.api.model.TeamAccount;

@Service
public class ScheduledProcessor implements Processor {
 
    private final AtomicInteger counter = new AtomicInteger();
    
    @Autowired
    private HBaseManager hBaseManager;
    
    @Autowired
    private EmailController emailController;
    
    @Scheduled(fixedDelay = 60000 * 60)
    public void processEmailReminder() {
        
        List<Team> teams = hBaseManager.getTeams();
        for (Team team : teams) {
        	
        	for (TeamAccount teamAccount : team.getTeamMembers()) {        		
        		if (teamAccount.isActive()) {
        			        
        			//Send email.
        			
        		}        		
        	}
        	
        }        
    }
}
