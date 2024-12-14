package com.api.user_management.ui.model.response;
import java.util.List;

public class JwtAuthenticationResponse {
    
	private String accessToken;
    private String userId;
    private String userType;
    private String userStatus;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
	private String websiteLink;
	private String address;
	private String linkedIn;
	private String twitter;
	private String departmentWebsite;
	private boolean isPrivacyDisabled;
	
	public boolean getIsPrivacyDisabled() {
		return isPrivacyDisabled;
	}
	public void setPrivacyDisabled(boolean isPrivacyDisabled) {
		this.isPrivacyDisabled = isPrivacyDisabled;
	}
    public String getWebsiteLink() {
		return websiteLink;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getDepartmentWebsite() {
		return departmentWebsite;
	}
	public void setDepartmentWebsite(String departmentWebsite) {
		this.departmentWebsite = departmentWebsite;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
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
