package com.api.user_management.service.Impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.exception.AppException;
import com.api.user_management.io.entity.RoleEntity;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.entity.UserRolesEntity;
import com.api.user_management.io.repositories.RoleRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.io.repositories.UserRolesRepository;
import com.api.user_management.service.RolesService;
import com.api.user_management.ui.model.request.AddRoleRequestModel;
import com.api.user_management.ui.model.request.UserRoleRequestModel;
import com.api.user_management.ui.model.response.RoleResponseModel;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRolesRepository userRoleRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public RoleResponseModel addRole(AddRoleRequestModel roleDetails) {

		RoleResponseModel returnValue = new RoleResponseModel();
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(roleDetails, roleEntity);
//		roleEntity.setRoleFullName("ROLE_" + roleEntity.getRoleName());
		roleRepository.save(roleEntity);
//		if(savedRole != null) {
//			returnValue="Role Added";
//		}
		return returnValue;
	}

	@Override
	public String updateRole(long roleId, AddRoleRequestModel roleDetails) {

		String returnValue = "";

		RoleEntity roleEntity = roleRepository.findByIdAndIsDeleted(roleId, false);
		if (roleEntity == null)
			throw new AppException("User RoleEntity not found.");

		BeanUtils.copyProperties(roleDetails, roleEntity);
//		roleEntity.setRoleFullName("ROLE_" + roleDetails.getRoleName());
		RoleEntity savedRole = roleRepository.save(roleEntity);
		if (savedRole != null) {
			returnValue = "Role Updated";
		}

		return returnValue;
	}

	@Override
	public String deleteRole(Long roleId) {
		// TODO Auto-generated method stub
		RoleEntity roleEntity = roleRepository.findByIdAndIsDeleted(roleId, false);

		if (roleEntity == null)
			throw new AppException("Role not found");

		roleEntity.setDeleted(true);
		roleRepository.save(roleEntity);
		return "Data deleted";

	}

	@Override
	public String saveUserRole(UserRoleRequestModel userRoleDetail) {
		// TODO Auto-generated method stub
		UserRolesEntity userRolesEntity = new UserRolesEntity();
		BeanUtils.copyProperties(userRoleDetail, userRolesEntity);
		userRoleRepository.save(userRolesEntity);
		return "Data Added";
	}

	@Override
	public String updateUserRole(UserRoleRequestModel userRoleDetail, Long userRoleId) {
		// TODO Auto-generated method stub
		UserRolesEntity userRolesEntity = userRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId,false);
		if (userRolesEntity == null)
			throw new AppException("Data not found");

		BeanUtils.copyProperties(userRoleDetail, userRolesEntity);
		userRoleRepository.save(userRolesEntity);
		return "Data updated!";
	}

	@Override
	public String deleteUserRole(Long userRoleId) {
		// TODO Auto-generated method stub
		UserRolesEntity userRolesEntity = userRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, false);

		if (userRolesEntity == null)
			throw new AppException("Role not found");
		userRolesEntity.setDeleted(true);
		userRoleRepository.save(userRolesEntity);
		return "Data deleted!";

	}

	@Override
	public List<RoleResponseModel> getRoles(String searchKey, int page, int limit) {
		List<RoleResponseModel> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("roleName").ascending());
		Page<RoleEntity> rolePage = null;

		if ("".equals(searchKey))
			rolePage = roleRepository.findByIsDeleted(false, pageableRequest);// .findAll(pageableRequest);
		else
			rolePage = roleRepository.findByIsDeletedAndRoleNameContaining(false, searchKey, pageableRequest);// .findAll(pageableRequest);

		List<RoleEntity> roleEntities = rolePage.getContent();

		int totalPages = rolePage.getTotalPages();
		for (RoleEntity roleEntity : roleEntities) {

			RoleResponseModel roleResponseModel = new RoleResponseModel();
			BeanUtils.copyProperties(roleEntity, roleResponseModel);

			UserEntity userEntity = userRepository.findByUserId(roleEntity.getCreatedBy());
			if (userEntity != null)
				roleResponseModel.setCreatedBy(userEntity.getFirstName() + " " + userEntity.getLastName());
			else
				roleResponseModel.setCreatedBy("");

			if (returnValue.size() == 0) {
				roleResponseModel.setTotalPage(totalPages);
			}

			returnValue.add(roleResponseModel);
		}
		return returnValue;
	}

	@Override
	public RoleResponseModel getRole(Long roleId) {
		// TODO Auto-generated method stub
		RoleResponseModel returnValue = new RoleResponseModel();
		RoleEntity roleEntity = roleRepository.findByIdAndIsDeleted(roleId, false);
		if(roleEntity==null) throw new AppException("No role with this id");
		BeanUtils.copyProperties(roleEntity, returnValue);
		return returnValue;
	}

}
