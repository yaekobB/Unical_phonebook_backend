package com.api.user_management.io.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.api.model.audit.Audit;

@Entity(name="users")
public class UserEntity extends Audit implements Serializable  {

	private static final long serialVersionUID = -1500349168344396612L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false,unique = true)
	private String userId;
	
	@Column(nullable = false,length=50)
	private String firstName;
	
	@Column(nullable = false,length=50)
	private String lastName;
	
	@Column(nullable = false,length=50)
	private String middleName;
	
	@Column(nullable = false,length=50)
	private String phoneNumber;
	
	@Column(nullable = true,length=50)
	private String websiteLink;
	
	@Column(nullable = true,length=50)
	private String address;
	
	@Column(nullable = true,length=50)
	private String linkedIn;
	
	@Column(nullable = true,length=50)
	private String twitter;
	
	@Column(nullable = true,length=50)
	private String departmentWebsite;
	
	@Column(nullable = false, unique=true)
	private String email;
	
	@Column(nullable = false)
	private String encryptedPassword;
	 
	private String emailVerificationToken;
	
	@Column(nullable = true)
	private String passwordResetCode;
	
	@Column(nullable = false)
	private String userType;
	
	@Column(nullable = false)
	private String userStatus;
	
	@Column()
	private Integer verificationCode;
	
	@Column(nullable = false)
	private boolean isUserVerified=false;
	
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
	public Integer getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(Integer verificationCode) {
		this.verificationCode = verificationCode;
	}
	public boolean isUserVerified() {
		return isUserVerified;
	}
	public void setUserVerified(boolean isUserVerified) {
		this.isUserVerified = isUserVerified;
	}
	public UserEntity() {
		super();
	}
	public UserEntity(long id, String userId, String firstName, String lastName, String middleName, String phoneNumber,
			String websiteLink, String email, String encryptedPassword, String emailVerificationToken,
			String passwordResetCode, String userType, String userStatus, Date registrationDate, Integer departmentId,
			String department, Set<RoleEntity> roleEntities) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.phoneNumber = phoneNumber;
		this.websiteLink = websiteLink;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.emailVerificationToken = emailVerificationToken;
		this.passwordResetCode = passwordResetCode;
		this.userType = userType;
		this.userStatus = userStatus;
		this.registrationDate = registrationDate;
		this.departmentId = departmentId;
		this.department = department;
		this.roleEntities = roleEntities;
	}

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date registrationDate;
	
	@Column
	private Integer departmentId;
	
	@Column
	private String department;
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roleEntities = new HashSet<>();

	
	
	public Set<RoleEntity> getRoles() {
		return roleEntities;
	}

	public void setRoles(Set<RoleEntity> roleEntities) {
		this.roleEntities = roleEntities;
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

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
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

	public String getPasswordResetCode() {
		return passwordResetCode;
	}

	public void setPasswordResetCode(String passwordResetCode) {
		this.passwordResetCode = passwordResetCode;
	}

	public Set<RoleEntity> getRoleEntities() {
		return roleEntities;
	}

	public void setRoleEntities(Set<RoleEntity> roleEntities) {
		this.roleEntities = roleEntities;
	}
}

	
