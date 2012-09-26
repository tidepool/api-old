package com.tidepool.api.model;

import java.io.Serializable;

public class CodedAttributeGroup implements Serializable {

	private static final long serialVersionUID = 0L;

	public String element_group_id;
	public String element_group;
	
	public String getElement_group_id() {
		return element_group_id;
	}
	public void setElement_group_id(String element_group_id) {
		this.element_group_id = element_group_id;
	}
	public String getElement_group() {
		return element_group;
	}
	public void setElement_group(String element_group) {
		this.element_group = element_group;
	}
	
	
}
