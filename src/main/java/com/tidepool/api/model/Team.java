package com.tidepool.api.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

public class Team {

	public static final byte[] owner_id_column = Bytes.toBytes("owner_id");
	public static final byte[] name_column = Bytes.toBytes("name");
	public static final byte[] timeline_column = Bytes.toBytes("timeline");
	private final SimpleDateFormat dateFormat =new SimpleDateFormat("mm/dd/yyyy");
	
	public long id;
	public String name;
	public String ownerId;
	public List<TeamAccount> teamMembers = new ArrayList<TeamAccount>();
	public long timeline;
	
	
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public long getTimeline() {
		return timeline;
	}
	public void setTimeline(long timeline) {
		this.timeline = timeline;
	}
	
	public String getFormattedTimeline() {
		return dateFormat.format(new Date(timeline));
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<TeamAccount> getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(List<TeamAccount> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
}
