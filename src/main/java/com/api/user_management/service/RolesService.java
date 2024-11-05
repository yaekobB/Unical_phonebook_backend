package com.api.user_management.service;

import java.util.List;
import com.api.user_management.ui.model.request.AddRoleRequestModel;
import com.api.user_management.ui.model.request.UserRoleRequestModel;
import com.api.user_management.ui.model.response.RoleResponseModel;

public interface RolesService {

	RoleResponseModel addRole(AddRoleRequestModel roleDetails);

	String updateRole(long roleId, AddRoleRequestModel roleDetails);

	String deleteRole(Long roleId);

	String saveUserRole(UserRoleRequestModel userRoleDetail);

	String updateUserRole(UserRoleRequestModel userRoleDetail, Long userRoleId);

	String deleteUserRole(Long userRoleId);

	List<RoleResponseModel> getRoles(String searchKey, int page, int limit);

	RoleResponseModel getRole(Long roleId);

}
