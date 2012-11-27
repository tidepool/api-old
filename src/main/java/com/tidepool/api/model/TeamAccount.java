package com.tidepool.api.model;

import org.apache.hadoop.hbase.util.Bytes;

public class TeamAccount {

	public static final byte[] team_id_column = Bytes.toBytes("team_id");
	public static final byte[] account_id_column = Bytes.toBytes("account_id");
	public static final byte[] active_column = Bytes.toBytes("active");
	
	public long id;
	public Account account;
	public long teamId;
	public boolean active;
	
	
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
