package com.tidepool.api.model;

import java.util.ArrayList;
import java.util.List;

public class CodingEventRollup {

	public List<CodingEvent> events = new ArrayList<CodingEvent>();
	
	public CodingEventRollup(List<CodingEvent> events) {
		this.events = events;
	}
	
	public List<CodingEvent> getSelectedEvents() {
		return getEventsByType(CodingEvent.SELECTED_TYPE);
	}
	
	public List<CodingEvent> getViewedEvents() {
		return getEventsByType(CodingEvent.VIEWED_TYPE);
	}
	
	public List<CodingEvent> getCancelEvents() {
		return getEventsByType(CodingEvent.CANCEL_TYPE);
	}
	
	public List<CodingEvent> getEventsByType(String type) {
		 List<CodingEvent> selectionEvents = new ArrayList<CodingEvent>();
		 for (CodingEvent event : events) {
			 if (event.getType().equals(type)) {
				 selectionEvents.add(event);
			 }
		 }
		 return selectionEvents;
	}
	
	
	
}
