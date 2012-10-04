package com.tidepool.api.model;

import java.io.Serializable;

import org.apache.hadoop.hbase.util.Bytes;

public class CodedAttribute implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	public static final byte[] element_group_id_column = Bytes.toBytes("element_group_id");
	
	
	public String element_name;
	public String element;
	public String element_description;
	public String element_group_id;
	public boolean highlightable = true;
	public boolean active = false;
	
	public CodedAttribute clone() {
		CodedAttribute attribute = new CodedAttribute();
		attribute.setElement(element);
		attribute.setElement_description(element_description);
		attribute.setElement_name(element_name);
		attribute.setHighlightable(highlightable);
		attribute.setActive(active);
		return attribute;
		
	}
	
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

	public String getElement_group_id() {
		return element_group_id;
	}

	public void setElement_group_id(String element_group_id) {
		this.element_group_id = element_group_id;
	}
	
	
	
}
