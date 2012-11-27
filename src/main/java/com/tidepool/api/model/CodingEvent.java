package com.tidepool.api.model;

import java.io.Serializable;

import org.apache.hadoop.hbase.util.Bytes;

public class CodingEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final byte[] user_id_column = Bytes.toBytes("user_id");
	public static final byte[] picture_id_column = Bytes.toBytes("picture_id");
	public static final byte[] element_column = Bytes.toBytes("element");
	public static final byte[] type_column = Bytes.toBytes("type");
	public static final byte[] text_column = Bytes.toBytes("text");
	public static final byte[] x0_column = Bytes.toBytes("x0");
	public static final byte[] y0_column = Bytes.toBytes("y0");
	public static final byte[] x1_column = Bytes.toBytes("x1");
	public static final byte[] y1_column = Bytes.toBytes("y1");
	public static final byte[] width_column = Bytes.toBytes("width");
	public static final byte[] height_column = Bytes.toBytes("height");
	
	public static final String VIEWED_TYPE =   "viewed";
	public static final String SELECTED_TYPE = "selected";
	public static final String CANCEL_TYPE =   "cancel";
	
	
	public String id;
	public long timestamp;
	public String user_id;
	public String picture_id;
	public String element;
	public String type;
	public String text;
	public String x0;
	public String y0;
	public String x1;
	public String y1;
	public String width;
	public String height;
	public String startTime;
	public String endTime;
	public String ip;
	public String resolution;
	public String screenHeight;
	public String screenWidth;
	public String userAgent;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getX0() {
		return x0;
	}
	public void setX0(String x0) {
		this.x0 = x0;
	}
	public String getY0() {
		return y0;
	}
	public void setY0(String y0) {
		this.y0 = y0;
	}
	public String getX1() {
		return x1;
	}
	public void setX1(String x1) {
		this.x1 = x1;
	}
	public String getY1() {
		return y1;
	}
	public void setY1(String y1) {
		this.y1 = y1;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static byte[] getUserIdColumn() {
		return user_id_column;
	}
	public static byte[] getPictureIdColumn() {
		return picture_id_column;
	}
	public static byte[] getElementColumn() {
		return element_column;
	}
	public static byte[] getTypeColumn() {
		return type_column;
	}
	public static byte[] getTextColumn() {
		return text_column;
	}
	public static byte[] getX0Column() {
		return x0_column;
	}
	public static byte[] getY0Column() {
		return y0_column;
	}
	public static byte[] getX1Column() {
		return x1_column;
	}
	public static byte[] getY1Column() {
		return y1_column;
	}
	public static byte[] getWidthColumn() {
		return width_column;
	}
	public static byte[] getHeightColumn() {
		return height_column;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(String screenHeight) {
		this.screenHeight = screenHeight;
	}
	public String getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(String screenWidth) {
		this.screenWidth = screenWidth;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
}
