package com.api.user_management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user_management.service.DepartmentService;
import com.api.user_management.ui.model.request.DepartmentRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentsService;
	
	@PostMapping
	public DepartmentResponseModel saveDepartments(@RequestBody DepartmentRequestModel departmentsDetail) {
		
		DepartmentResponseModel returnValue = departmentsService.addDepartment(departmentsDetail);
		
		return returnValue;
		
	}
	@PutMapping(path = "/{departmentId}")
	public String updateDepartments(@RequestBody DepartmentRequestModel departmentDetails, @PathVariable Integer departmentId) {
		String returnValue = departmentsService.updateDepartment(departmentId, departmentDetails);
		return returnValue;
	}
	
	@GetMapping(path = "/{departmentId}")
	public DepartmentResponseModel getDepartment(@PathVariable Integer departmentId) {
		DepartmentResponseModel returnValue = departmentsService.getDepartment(departmentId);
		return returnValue;
	}
	
	@GetMapping
	public List<DepartmentResponseModel> getDepartments(@RequestParam(value="searchKey", defaultValue = "") String searchKey, @RequestParam(value="page", defaultValue = "1") int page,
			   @RequestParam(value="limit", defaultValue = "1000") int limit) {
		List<DepartmentResponseModel> returnValue = departmentsService.getDepartments(searchKey,page,limit);
		return returnValue;
	}

	@DeleteMapping(path = "/{departmentId}")
	public String deleteDepartment(@PathVariable Integer departmentId) {
		String returnValue = departmentsService.deleteDepartment(departmentId);
	    return returnValue;
	}
}
