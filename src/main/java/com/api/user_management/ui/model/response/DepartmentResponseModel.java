package com.api.user_management.ui.model.response;

public class DepartmentResponseModel {

	private Integer departmentId;
	private String departmentName;
	private String departmentLink;
	private String createdBy;
	private Integer totalPage;

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
