package com.tidepool.api.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CodedItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public HashMap<String, Highlight> highlightMap = new HashMap<String, Highlight>();
	
	
	public boolean isAttributeActive(String attributeName) {
		Class<CodedItem> codedItemClass = CodedItem.class;
		try {
			Field field = codedItemClass.getField(attributeName);
			try {
				if (field.getInt(this) > 0) {
					return true;
				}
			} catch (IllegalArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			}
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchFieldException e) {
			return false;
		}
		return false;
	}
	
	
	public String id;
	public String pic_name;
	public String picure_id;
	public String primary_color_word;
	public int mystery;
	public int vista;
	public int anger;
	public int disgust;
	public int fear;
	public int happy;
	public int sad;
	public int surprise;
	public int whole;
	public int detail;
	public int negative_space;
	public int movement;
	public int color;
	public int b_w;
	public int shading;
	public int texture;
	public int pair;
	public int human;
	public int animal;
	public int abstraction;
	public int nature;
	public int man_made;
	public int direct_eyes;
	public int averted_eyes;
	public int human_eyes;
	public int animal_eyes;
	public int bird;
	public int flying_bird;
	public int domestic_animal;
	public int non_domestic;
	public int alone;
	public int coworker;
	public int family;
	public int romantic;
	public int sibling;
	public int friends;
	public int strangers;
	public int beach;
	public int desert;
	public int flower;
	public int food;
	public int forest;
	public int indoor;
	public int mountain;
	public int night_life;
	public int ocean;
	public int park;
	public int restaurant;
	public int river;
	public int rock_climbing;
	public int snow;
	public int suburban;
	public int sunset;
	public int urban;
	public int water;
	public int male;
	public int female;
	public int infant;
	public int child;
	public int pre_teen;
	public int teenager;
	public int young_adult;
	public int adult;
	public int elder;
	public int african_american;
	public int caucasian;
	public int asian;
	public int hispanic;
	public int indian;
	public int native_american;
	public String primary_color;
	public int red;
	public int green;
	public int blue;
	
	public HashMap<String, Highlight> getHighlightMap() {
		return highlightMap;
	}
	public void setHighlightMap(HashMap<String, Highlight> highlightMap) {
		this.highlightMap = highlightMap;
	}
	
	
	
}
