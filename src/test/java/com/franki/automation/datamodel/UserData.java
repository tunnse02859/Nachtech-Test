package com.franki.automation.datamodel;

public class UserData {
	
	private String token;
	private String userID;
	private String username;
	private String password;
	private String displayName;
	private String fullName;

	// Token
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
	
	// UerID
	public void setUserId(String userId) {
		this.userID = userId;
	}
	
	public String getUserId() {
		return this.userID;
	}
	
	// Username
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	// Password
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	// Display Name
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	// Full Name
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	

}
