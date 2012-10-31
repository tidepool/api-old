package com.tidepool.api.model;

import java.util.HashMap;

import org.apache.hadoop.hbase.util.Bytes;

public class RowMapper {
	
	public static final byte[] big_5_column = Bytes.toBytes("big_5");
	public static final byte[] factor_column = Bytes.toBytes("factor");
	public static final byte[] value_column = Bytes.toBytes("value");
	
	public String name;
	
	public HashMap<String, Double> doubleValues = new HashMap<String, Double>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Double> getDoubleValues() {
		return doubleValues;
	}

	public void setDoubleValues(HashMap<String, Double> doubleValues) {
		this.doubleValues = doubleValues;
	}
	
}
