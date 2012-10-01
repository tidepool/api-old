package com.tidepool.api.model;

import org.apache.hadoop.hbase.util.Bytes;

public class MainGroup {
	
	
	public static final byte[] element_name_column = Bytes.toBytes("element_group");
	public static final byte[] element_label_column = Bytes.toBytes("element_group_name");
	public static final byte[] element_description_column = Bytes.toBytes("element_group_description");
	
	public String id;
	public String name;
	public String label;
	public String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
