package com.api.user_management.ui.model.response;

public class UserRest {
	
	private long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String phoneNumber;
	private String userType;
	private String userStatus;
	private String department;
	private String websiteLink;
	private String address;
	private String linkedIn;
	private String twitter;
	private String departmentWebsite;
	private long totalUsers;
	private boolean isPrivacyDisabled;

	public boolean getIsPrivacyDisabled() {
		return isPrivacyDisabled;
	}
	public void setPrivacyDisabled(boolean isPrivacyDisabled) {
		this.isPrivacyDisabled = isPrivacyDisabled;
	}
    public long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
	private DepartmentResponseModel departmentResponseModel;
	
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
	public DepartmentResponseModel getDepartmentResponseModel() {
		return departmentResponseModel;
	}
	public void setDepartmentResponseModel(DepartmentResponseModel departmentResponseModel) {
		this.departmentResponseModel = departmentResponseModel;
	}
	private int totalPages;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
