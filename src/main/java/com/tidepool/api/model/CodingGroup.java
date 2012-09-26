package com.tidepool.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CodingGroup implements Serializable {

	private static final long serialVersionUID = 0L;
	
	public String id;
	public List<CodedAttributeGroup> attributes = new ArrayList<CodedAttributeGroup>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CodedAttributeGroup> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<CodedAttributeGroup> attributes) {
		this.attributes = attributes;
	}

}
