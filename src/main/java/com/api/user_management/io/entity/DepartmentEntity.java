package com.api.user_management.io.entity;

import javax.persistence.*;

import com.api.model.audit.Audit;

@Entity
@Table(name = "departments")
public class DepartmentEntity extends Audit {

	private static final long serialVersionUID = 7998053427217953234L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer departmentId;

	@Column(nullable = false, unique = true)
	private String departmentName;

	@Column(nullable = false)
	private String departmentLink;

	public DepartmentEntity() {

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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


}
