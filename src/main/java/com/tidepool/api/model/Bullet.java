package com.tidepool.api.model;

import java.io.Serializable;
import java.util.List;

public class Bullet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String title;
	public String subtitle;
	public List<Double> ranges;
	public List<Double> measures;
	public List<Double> markers;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public List<Double> getRanges() {
		return ranges;
	}
	public void setRanges(List<Double> ranges) {
		this.ranges = ranges;
	}
	public List<Double> getMeasures() {
		return measures;
	}
	public void setMeasures(List<Double> measures) {
		this.measures = measures;
	}
	public List<Double> getMarkers() {
		return markers;
	}
	public void setMarkers(List<Double> markers) {
		this.markers = markers;
	}
	
}
