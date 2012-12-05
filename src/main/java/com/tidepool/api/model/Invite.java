package com.tidepool.api.model;

import org.apache.hadoop.hbase.util.Bytes;

public class Invite {

	public static final String OPEN_STATUS = "OPEN";	
	public static final String CLOSED_STATUS = "CLOSED";
	
	public static final byte[] owner_id_column = Bytes.toBytes("owner_id");
	public static final byte[] account_id_column = Bytes.toBytes("account_id");
	public static final byte[] team_id_column = Bytes.toBytes("team_id");
	public static final byte[] secret_column = Bytes.toBytes("secret");
	public static final byte[] status_column = Bytes.toBytes("status");
	
	public long id;
	public String ownerId;
	public String accountId;
	public long teamId;
	public String secret;
	public String status;
	
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
