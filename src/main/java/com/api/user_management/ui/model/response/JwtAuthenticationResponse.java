package com.api.user_management.ui.model.response;

import java.util.List;


public class JwtAuthenticationResponse {
    
	private String accessToken;
    private String userId;
    private String userType;
    private String userStatus;
    List<RoleResponseForLogin> roles;
    
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<RoleResponseForLogin> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleResponseForLogin> roles) {
		this.roles = roles;
	}

    
}
