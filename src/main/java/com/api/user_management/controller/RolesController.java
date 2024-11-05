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
import com.api.user_management.service.RolesService;
import com.api.user_management.ui.model.request.AddRoleRequestModel;
import com.api.user_management.ui.model.request.UserRoleRequestModel;
import com.api.user_management.ui.model.response.RoleResponseModel;

@RestController
@RequestMapping("/roles")
public class RolesController {
	
	@Autowired
	RolesService rolesService;
	
	@PostMapping
	public RoleResponseModel saveRoles(@RequestBody AddRoleRequestModel rolesDetail) {
		
		RoleResponseModel returnValue = rolesService.addRole(rolesDetail);
		
		return returnValue;
		
	}
	@PutMapping(path = "/{roleId}")
	public String updateRoles(@RequestBody AddRoleRequestModel roleDetails, @PathVariable Long roleId) {
		String returnValue = rolesService.updateRole(roleId, roleDetails);
		return returnValue;
	}
	
	@GetMapping(path = "/{roleId}")
	public RoleResponseModel getRole(@PathVariable Long roleId) {
		RoleResponseModel returnValue = rolesService.getRole(roleId);
		return returnValue;
	}
	
	@GetMapping
	public List<RoleResponseModel> getRoles(@RequestParam(value="searchKey", defaultValue = "") String searchKey, @RequestParam(value="page", defaultValue = "1") int page,
			   @RequestParam(value="limit", defaultValue = "1000") int limit) {
		List<RoleResponseModel> returnValue = rolesService.getRoles(searchKey,page,limit);
		return returnValue;
	}

	@DeleteMapping(path = "/{roleId}")
	public String deleteRole(@PathVariable Long roleId) {
		String returnValue = rolesService.deleteRole(roleId);
	    return returnValue;
	}
	@PostMapping(path = "/user-role")
	public String saveUserRole(@RequestBody UserRoleRequestModel userRoleDetail) {
		String returnValue = rolesService.saveUserRole(userRoleDetail);
		return returnValue;
		
	}
	@PutMapping(path = "/user-role/{userRoleId}")
	public String updateUserRole(@RequestBody UserRoleRequestModel userRoleDetail, @PathVariable Long userRoleId) {
		String returnValue = rolesService.updateUserRole(userRoleDetail,userRoleId);
		return returnValue;
	}
	
	@DeleteMapping(path = "/user-role/{userRoleId}")
	public String deleteUserRole(@PathVariable Long userRoleId) {
		String returnValue = rolesService.deleteUserRole(userRoleId);
		
		
		return returnValue;
	}
}
