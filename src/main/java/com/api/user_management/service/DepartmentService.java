package com.api.user_management.service;

import java.util.List;
import com.api.user_management.ui.model.request.DepartmentRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;

public interface DepartmentService {

	DepartmentResponseModel addDepartment(DepartmentRequestModel departmentsDetail);

	String updateDepartment(Integer departmentId, DepartmentRequestModel departmentDetails);

	DepartmentResponseModel getDepartment(Integer departmentId);

	List<DepartmentResponseModel> getDepartments(String searchKey, int page, int limit);

	String deleteDepartment(Integer departmentId);

}
