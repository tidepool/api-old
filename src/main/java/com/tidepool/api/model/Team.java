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
	public static final byte[] invite_subject_column = Bytes.toBytes("invite_subject");
	public static final byte[] invite_body_column = Bytes.toBytes("invite_body");
	public static final byte[] invite_reminder_column = Bytes.toBytes("invite_reminder");
	
	private final SimpleDateFormat dateFormat =new SimpleDateFormat("mm/dd/yyyy");
	
	public long id;
	public String name;
	public String ownerId;
	public List<TeamAccount> teamMembers = new ArrayList<TeamAccount>();
	public long timeline;
	
	public String inviteSubject;
	public String inviteBody;
	public String inviteReminder;
	
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
	public String getInviteSubject() {
		return inviteSubject;
	}
	public void setInviteSubject(String inviteSubject) {
		this.inviteSubject = inviteSubject;
	}
	public String getInviteBody() {
		return inviteBody;
	}
	public void setInviteBody(String inviteBody) {
		this.inviteBody = inviteBody;
	}
	public String getInviteReminder() {
		return inviteReminder;
	}
	public void setInviteReminder(String inviteReminder) {
		this.inviteReminder = inviteReminder;
	}
	
}
