package com.tidepool.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

public class Factor implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final byte[] factor_column = Bytes.toBytes("factor");
	public static final byte[] element_column = Bytes.toBytes("element");	
	public static final byte[] big_5_column = Bytes.toBytes("big_5");
	public static final byte[] value_column = Bytes.toBytes("value");
	
	protected int id;
	protected String name;
	protected String element;
	protected double coefficient;
	protected List<String> elements = new ArrayList<String>();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	
}
