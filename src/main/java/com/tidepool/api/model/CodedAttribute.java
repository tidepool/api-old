package com.tidepool.api.model;

import java.io.Serializable;

public class CodedAttribute implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String element_name;
	public String element;
	public String element_description;
	public boolean highlightable = true;
	public boolean active = false;
	
	public String getElement_name() {
		return element_name;
	}
	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getElement_description() {
		return element_description;
	}
	public void setElement_description(String element_description) {
		this.element_description = element_description;
	}
	public boolean isHighlightable() {
		return highlightable;
	}
	public void setHighlightable(boolean highlightable) {
		this.highlightable = highlightable;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
