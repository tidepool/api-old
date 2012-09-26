package com.tidepool.api.model;

import java.io.Serializable;

public class Highlight implements Serializable {

	public double x0;
	public double y0;
	public double x1;
	public double y1;
	public double height;
	public double width;
	
	public double getX0() {
		return x0;
	}
	public void setX0(double x0) {
		this.x0 = x0;
	}
	public double getY0() {
		return y0;
	}
	public void setY0(double y0) {
		this.y0 = y0;
	}
	public double getX1() {
		return x1;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	public double getY1() {
		return y1;
	}
	public void setY1(double y1) {
		this.y1 = y1;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	
	
	
}
