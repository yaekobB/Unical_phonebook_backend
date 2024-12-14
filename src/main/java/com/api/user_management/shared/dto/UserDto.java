package com.api.user_management.shared.dto;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

import com.api.user_management.ui.model.response.DepartmentResponseModel;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 559579346246652215L;
	private long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String middleName;
	private boolean isPrivacyDisabled;

	public boolean getIsPrivacyDisabled() {
		return isPrivacyDisabled;
	}
	public void setPrivacyDisabled(boolean isPrivacyDisabled) {
		this.isPrivacyDisabled = isPrivacyDisabled;
	}
	@NotBlank(message = "Email is mandatory")
    @Pattern(
        regexp = "^[\\w._%+-]+@[\\w.-]+\\.unicali\\.it$",
        message = "Email must be in the format 'string@string.unicali.it'"
    )
	private String email;
	private String password;
	private String phoneNumber;
	private String department;
	private Long roleId;
	private String userType;
	private String userStatus;
	private String userRole;
	private String encryptedPassword;
	private String emailVerificationToken;
	private Boolean emailVerficationStatus=false;
	private Date registrationDate;
	private int totalPages;
	private String websiteLink;
	private String address;
	private String linkedIn;
	private String twitter;
	private String departmentWebsite;
	private Integer departmentId;
	private long totalUsers;
	
    public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
	private DepartmentResponseModel departmentResponseModel;
	
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
	public UserDto() {
		super();
	}
	public UserDto(long l, String string, String string2, String string3, String string4, String string5,
			String string6, String string7, String string8, String string9, String string10, String string11,
			Object object, Object object2, Object object3, Object object4, Object object5) {
		// TODO Auto-generated constructor stub
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
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	
	
	
	public String getWebsiteLink() {
		return websiteLink;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String status) {
		this.userStatus = status;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}
	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}
	public Boolean getEmailVerficationStatus() {
		return emailVerficationStatus;
	}
	public void setEmailVerficationStatus(Boolean emailVerficationStatus) {
		this.emailVerficationStatus = emailVerficationStatus;
	}

}
