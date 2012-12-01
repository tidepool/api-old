package com.tidepool.api.model;

import org.apache.hadoop.hbase.util.Bytes;

public class Invite {

	public static final byte[] owner_id_column = Bytes.toBytes("owner_id");
	public static final byte[] account_id_column = Bytes.toBytes("account_id");
	public static final byte[] secret_column = Bytes.toBytes("secret");
	
	public long id;
	public String ownerId;
	public String accountId;
	public String secret;
	
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
	
}
