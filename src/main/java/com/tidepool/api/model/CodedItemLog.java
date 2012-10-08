package com.tidepool.api.model;

import org.apache.hadoop.hbase.util.Bytes;

public class CodedItemLog {

	
	public static final byte[] user_id_column = Bytes.toBytes("user_id");
	public static final byte[] explicit_image_id_column = Bytes.toBytes("explicit_image_id");
	
	public long id;
	public long userId;
	public String explicitImageId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getExplicitImageId() {
		return explicitImageId;
	}
	public void setExplicitImageId(String explicitImageId) {
		this.explicitImageId = explicitImageId;
	}
	
}
