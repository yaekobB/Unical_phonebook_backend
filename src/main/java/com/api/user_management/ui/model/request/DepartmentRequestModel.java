package com.api.user_management.ui.model.request;
public class DepartmentRequestModel {

	private String departmentName;
	private String departmentLink;
	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentLink() {
		return departmentLink;
	}

	public void setDepartmentLink(String departmentLink) {
		this.departmentLink = departmentLink;
	}

}
