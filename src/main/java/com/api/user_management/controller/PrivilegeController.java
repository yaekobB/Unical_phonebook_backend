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

import com.api.user_management.io.entity.RolePrivilegeEntity;
import com.api.user_management.service.PrivilegeService;
import com.api.user_management.ui.model.request.PrivilegeRequestModel;
import com.api.user_management.ui.model.request.RolePrivilegeRequestModel;
import com.api.user_management.ui.model.response.OperationStatusModel;
import com.api.user_management.ui.model.response.PrivilegeResponseModel;
import com.api.user_management.ui.model.response.RequestOperationName;
import com.api.user_management.ui.model.response.RequestOperationStatus;
import com.api.user_management.ui.model.response.RolePrivilegesResponseModel;

@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

	@Autowired
	PrivilegeService privilegeService;

	@PostMapping
	public PrivilegeResponseModel savePrivilege(@RequestBody PrivilegeRequestModel privilegeDetail) {
		
		PrivilegeResponseModel returnValue = privilegeService.savePrivilege(privilegeDetail);
		
		return returnValue;
	}

	@GetMapping(path = "/{privilegeId}")
	public PrivilegeResponseModel getPrivilege(@PathVariable Integer privilegeId) {
		PrivilegeResponseModel returnValue = privilegeService.getPrivilege(privilegeId);
		return returnValue;
	}

	@PutMapping(path = "/{privilegeId}")
	public PrivilegeResponseModel updatePrivilege(@RequestBody PrivilegeRequestModel privilegeDetail,
			@PathVariable Integer privilegeId) {

		PrivilegeResponseModel returnValue = privilegeService.updatePrivilege(privilegeDetail, privilegeId);
		return returnValue;
	}

	@GetMapping
	public List<PrivilegeResponseModel> getAllPrivileges(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey) {
		List<PrivilegeResponseModel> returnValue = privilegeService.getAllPrivileges(page, limit, searchKey);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{privilegeId}")
	public String deletePrivilege(@PathVariable Integer privilegeId) {
		String returnValue = privilegeService.deletePrivilege(privilegeId);
		
		return returnValue;
	}
	
	@PostMapping(path = "/role-privilege")
	public String saveRolePrivilegeData(@RequestBody RolePrivilegeRequestModel rolePrivilegeDetail) {
		String returnValue = privilegeService.saveRolePrivilegeData(rolePrivilegeDetail);
		return returnValue;
	}
	
	@PutMapping(path = "/role-privilege/{rolePrivilegeId}")
	public String updateRolePrivilegeData(@RequestBody RolePrivilegeRequestModel rolePrivilegeDetail,@PathVariable Long rolePrivilegeId) {
		String returnValue = privilegeService.updateRolePrivilegeData(rolePrivilegeDetail,rolePrivilegeId);
		return returnValue;
	}
	
	@DeleteMapping(path = "/role-privilege/{rolePrivilegeId}")
	public String deleteRolePrivilegeData(@PathVariable Long rolePrivilegeId) {
		String returnValue = privilegeService.deleteRolePrivilageData(rolePrivilegeId);
	
		return returnValue;
	}
	
	@GetMapping(path = "/role-privileges/{roleId}")
	public List<RolePrivilegesResponseModel> getRolePrivileges(@PathVariable Long roleId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "50") int limit,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey) {
		
		List<RolePrivilegesResponseModel> returnValue = privilegeService.getRolePrivileges(page, limit, searchKey, roleId);
		return returnValue;
	}

}
