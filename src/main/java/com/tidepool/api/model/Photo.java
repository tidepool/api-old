package com.tidepool.api.model;

import java.io.Serializable;

public class Photo implements Serializable {

	public String id;
	public String src;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
}
