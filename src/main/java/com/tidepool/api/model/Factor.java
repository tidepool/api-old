package com.tidepool.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

public class Factor implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final byte[] factor_column = Bytes.toBytes("factor");
	public static final byte[] element_column = Bytes.toBytes("element");
	
	protected String name;
	protected List<String> elements;
	protected int score;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getElements() {
		return elements;
	}
	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
