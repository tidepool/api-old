package com.tidepool.api.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.apache.hadoop.hbase.util.Bytes;

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
	public String picture_id;
	public String bucket_name;
	public String folder_name;
	
	public String primary_color_word;
	
	
	//Elements. To generate see the HBaseTest class.
	public int asymmetrical;
	public int mountain;
	public int filtered;
	public int strangers;
	public int makeup;
	public int fish;
	public int jewelry;
	public int storm;
	public int caucasian;
	public int aggression;
	public int circular;
	public int danger;
	public int bisexual;
	public int nature;
	public int movement;
	public int snow;
	public int buildings;
	public int deceased_humans;
	public int alone;
	public int fetishes;
	public int suburban;
	public int church;
	public int alcoholic_drinks;
	public int drugs;
	public int coworkers;
	public int texture;
	public int art;
	public int theater;
	public int museum;
	public int kitchen;
	public int transgender;
	public int blood;
	public int animal_eyes;
	public int temple;
	public int category;
	public int technological_equipment;
	public int stars;
	public int abstraction;
	public int southeast_asian;
	public int trees;
	public int homosexual;
	public int human_averted_eyes;
	public int surprise;
	public int religious_icons;
	public int native_american;
	public int animal_direct_eyes;
	public int pair;
	public int ocean;
	public int man_made;
	public int living_room;
	public int negative_space;
	public int motorcycles;
	public int symmetrical;
	public int happy;	
	public int weapons;
	public int water;
	public int primary_color;
	public int desert;
	public int park;
	public int green;
	public int male;
	public int deceased_animals;
	public int african_american;
	public int bird;
	public int reptiles;
	public int blurred;
	public int red;
	public int human_eyes;
	public int sunset;
	public int pet;
	public int dining;
	public int repins;
	public int comments;
	public int fear;
	public int nudity;
	public int mystery;
	public int infant;
	public int cuts_wounds_mutilation_etc;
	public int beach;
	public int insects;
	public int non_religious_spiritual_activities;
	public int bathroom;
	public int romance_romantic;
	public int asian;
	public int friends;
	public int family;
	public int cars;
	public int teenager;
	public int combat;
	public int flowers;
	public int kissing;
	public int forest;
	public int scatological;
	public int piercings;
	public int rural;
	public int bedroom;
	public int young_adult;
	public int high_fashion;
	public int anger;
	public int food;
	public int whole;
	public int likes;
	public int team_sports;
	public int significant_other;
	public int shopping;
	public int individual_athletics;
	public int moon;
	public int sex;
	public int reflection;
	public int african;
	public int dining_room;
	public int sadness;
	public int mosque;
	public int indoors;
	public int restaurant;
	public int achromatic;
	public int hispanic;
	public int bar ;
	public int elder;
	public int disgust;
	public int child;
	public int bdsm;
	public int details;
	public int domestic_animal;
	public int rocks;
	public int crowd;
	public int rain;
	public int animal;
	public int gym_exercise_equipment;
	public int night_life;
	public int flying_bird;
	public int shading;
	public int non_domestic_animal;
	public int adult;
	public int speed ;
	public int siblings;
	public int human;
	public int gambling;
	public int urban;
	public int cigarettes;
	public int human_direct_eyes;
	public int female;
	public int blue;
	public int color;
	public int tattoos;
	public int vista;
	public int night;
	public int animal_averted_eyes;
	public int heterosexual;
	public int disabled;
	public int religious_activities;
	public int river;
	
	public HashMap<String, Highlight> getHighlightMap() {
		return highlightMap;
	}
	public void setHighlightMap(HashMap<String, Highlight> highlightMap) {
		this.highlightMap = highlightMap;
	}
	public String getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}
	public String getBucket_name() {
		return bucket_name;
	}
	public void setBucket_name(String bucket_name) {
		this.bucket_name = bucket_name;
	}
	public String getFolder_name() {
		return folder_name;
	}
	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}
	
	
	
}
