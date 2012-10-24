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
				String value = (String)(field.get(this));
				if (value != null && value.equals("1")) {
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
	
	public String order_id;	
	public String asymmetrical;
	public String filtered;
	public String mountain;
	public String strangers;
	public String fish;
	public String makeup;
	public String sports_or_recreational_equipment;
	public String luggage;
	public String jewelry;
	public String storm;
	public String caucasian;
	public String airport;
	public String aggression;
	public String circular;
	public String danger;
	public String bisexual;
	public String nature;
	public String movement;
	public String snow;
	public String buildings;
	public String deceased_humans;
	public String alone;
	public String fetishes;
	public String books;
	public String church;
	public String suburban;
	public String honoraria;
	public String drugs;
	public String coworkers;
	public String alcoholic_drinks;
	public String airplane;
	public String texture;
	public String road_freeway;
	public String art;
	public String theater;
	public String kitchen;
	public String blood;
	public String museum;
	public String transgender;
	public String animal_eyes;
	public String temple;
	public String category;
	public String musical_instruments;
	public String abstraction;
	public String stars;
	public String technological_equipment;
	public String southeast_asian;
	public String trees;
	public String homosexual;
	public String human_averted_eyes;
	public String surprise;
	public String hospital;
	public String religious_icons;
	public String adaptive_or_medical_equipment;
	public String mirrors_glass;
	public String native_american;
	public String animal_direct_eyes;
	public String pair;
	public String large_equipment;
	public String vehicle;
	public String ocean;
	public String man_made;
	public String living_room;
	public String negative_space;
	public String motorcycles;
	public String symmetrical;
	public String happy;	
	public String lights_lighting;
	public String weapons;
	public String boat_ship;
	public String water;
	public String containers;
	public String primary_color;
	public String desert;
	public String park;
	public String green;
	public String cemetary;
	public String male;
	public String deceased_animals;
	public String african_american;
	public String stadiums_arenas;
	public String bird;
	public String blurred;
	public String reptiles;
	public String red;
	public String human_eyes;
	public String pet;
	public String sunset;
	public String dining;
	public String repins;
	public String comments;
	public String fear;
	public String nudity;
	public String mystery;
	public String primary_color_word;
	public String infant;
	public String cuts_wounds_mutilation_etc;
	public String beach;
	public String insects;
	public String furniture;
	public String non_religious_spiritual_activities;
	public String bathroom;
	public String romance_romantic;
	public String asian;
	public String tools;
	public String friends;
	public String sky;
	public String family;
	public String cars;
	public String teenager;
	public String combat;
	public String flowers;
	public String team_sports;
	public String kissing;
	public String forest;
	public String scatological;
	public String piercings;
	public String rural;
	public String bedroom;
	public String young_adult;
	public String high_fashion;
	public String food;
	public String anger;
	public String whole;
	public String likes;
	public String dam;
	public String significant_other;
	public String bridge;
	public String shopping;
	public String individual_athletics;
	public String reflection;
	public String moon;
	public String sex;
	public String dining_room;
	public String african;
	public String sadness;
	public String mosque;
	public String indoors;
	public String restaurant;
	public String achromatic;
	public String hispanic;
	public String bar ;
	public String elder;
	public String disgust;
	public String child;
	public String bdsm;
	public String details;
	public String domestic_animal;
	public String crowd;
	public String rocks;
	public String rain;
	public String animal;
	public String gym_exercise_equipment;
	public String night_life;
	public String flying_bird;
	public String shading;
	public String adult;
	public String non_domestic_animal;
	public String siblings;
	public String speed ;
	public String human;
	public String gambling;
	public String cigarettes;
	public String urban;
	public String human_direct_eyes;
	public String female;
	public String color;
	public String blue;
	public String tattoos;
	public String vista;
	public String animal_averted_eyes;
	public String night;
	public String heterosexual;
	public String disabled;
	public String religious_activities;
	public String river;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
}
