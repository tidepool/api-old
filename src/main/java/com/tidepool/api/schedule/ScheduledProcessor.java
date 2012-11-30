package com.tidepool.api.schedule;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledProcessor implements Processor {
 
    private final AtomicInteger counter = new AtomicInteger();
    
    @Scheduled(fixedDelay = 60000)
    public void process() {
        System.out.println("processing next at " + new Date());
        
        
        
    }
}
