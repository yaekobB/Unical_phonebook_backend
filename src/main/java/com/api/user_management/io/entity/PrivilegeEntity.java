package com.api.user_management.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.api.model.audit.Audit;

@Entity(name = "privilege")
public class PrivilegeEntity extends Audit implements Serializable {

	private static final long serialVersionUID = 2934971132565967009L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer privilegeId;

	@Column(nullable = false,unique = true)
	private String privilegeName;

	@Column(nullable = false)
	private String privilegeDescription;

	@Column
	private String privilegeUrl;

	@Column
	private String method;

	public String getPrivilegeDescription() {
		return privilegeDescription;
	}

	public void setPrivilegeDescription(String privilegeDescription) {
		this.privilegeDescription = privilegeDescription;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
