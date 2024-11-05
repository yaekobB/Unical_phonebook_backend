package com.api.user_management.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.exception.AppException;
import com.api.user_management.io.entity.PrivilegeEntity;
import com.api.user_management.io.entity.RolePrivilegeEntity;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.PrivilegeRepository;
import com.api.user_management.io.repositories.RolePrivilegeRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.PrivilegeService;
import com.api.user_management.ui.model.request.PrivilegeRequestModel;
import com.api.user_management.ui.model.request.RolePrivilegeRequestModel;
import com.api.user_management.ui.model.response.PrivilegeResponseModel;
import com.api.user_management.ui.model.response.RolePrivilegesResponseModel;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolePrivilegeRepository rolePrivilegeRepository;

	@Override
	public PrivilegeResponseModel savePrivilege(PrivilegeRequestModel privilegeDetail) {
		// TODO Auto-generated method stub
		List<PrivilegeEntity> privilegeEnti = null;
		privilegeEnti =  privilegeRepository.findByPrivilegeUrlAndMethodAndIsDeleted(privilegeDetail.getPrivilegeUrl(),privilegeDetail.getMethod(),false);
		
    	if(privilegeEnti.size()>0) throw new AppException("Privilege already exists with this Method.");
		PrivilegeResponseModel returnValue = new PrivilegeResponseModel();
		PrivilegeEntity privilegeEntity = new PrivilegeEntity();
		BeanUtils.copyProperties(privilegeDetail, privilegeEntity);
		PrivilegeEntity savedPrivilegeEntity = privilegeRepository.save(privilegeEntity);
		BeanUtils.copyProperties(savedPrivilegeEntity, returnValue);
		return returnValue;
	}

	@Override
	public PrivilegeResponseModel getPrivilege(Integer privilegeId) {
		// TODO Auto-generated method stub
		PrivilegeResponseModel returnValue = new PrivilegeResponseModel();
		PrivilegeEntity privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(privilegeId, false);
		if (privilegeEntity == null)
			throw new AppException("No Privilege with this Id");
		BeanUtils.copyProperties(privilegeEntity, returnValue);
		return returnValue;
	}

	@Override
	public PrivilegeResponseModel updatePrivilege(PrivilegeRequestModel privilegeDetail, Integer privilegeId) {
		PrivilegeResponseModel returnValue = new PrivilegeResponseModel();
		PrivilegeEntity privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(privilegeId, false);
		if (privilegeEntity == null)
			throw new AppException("No Privilege with this Id");
		BeanUtils.copyProperties(privilegeDetail, privilegeEntity);
		PrivilegeEntity savedPrivilegeEntity = privilegeRepository.save(privilegeEntity);
		BeanUtils.copyProperties(savedPrivilegeEntity, returnValue);
		return returnValue;
	}

	@Override
	public List<PrivilegeResponseModel> getAllPrivileges(int page, int limit, String searchKey) {
		List<PrivilegeResponseModel> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<PrivilegeEntity> agentPage = null;

		if ("".equals(searchKey))
			agentPage = privilegeRepository.findByIsDeleted(false,pageableRequest);// .findAll(pageableRequest);
		else
			agentPage = privilegeRepository.findByPrivilegeNameContainingOrPrivilegeUrlContainingAndIsDeleted(searchKey,
					searchKey,false,pageableRequest);// .findAll(pageableRequest);

		List<PrivilegeEntity> privileges = agentPage.getContent();

		int totalPages = agentPage.getTotalPages();
		for (PrivilegeEntity privilegeEntity : privileges) {

			PrivilegeResponseModel privilegeResponseModel = new PrivilegeResponseModel();
			BeanUtils.copyProperties(privilegeEntity, privilegeResponseModel);

			UserEntity userEntity = userRepository.findByUserId(privilegeEntity.getCreatedBy());
			
			if (returnValue.size() == 0) {
				privilegeResponseModel.setTotalPage(totalPages);
			}

			returnValue.add(privilegeResponseModel);
		}
		return returnValue;
	}

	@Override
	public String deletePrivilege(Integer privilegeId) {
		// TODO Auto-generated method stub
		PrivilegeEntity privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(privilegeId,false);
		if (privilegeEntity == null)
			throw new AppException("No Privilege with this Id");
		privilegeEntity.setDeleted(true);
		privilegeRepository.save(privilegeEntity);
//		return "Privilege Deleted!";
		return "Privilege Deleted!";
	}

	@Override
	public String saveRolePrivilegeData(RolePrivilegeRequestModel rolePrivilegeDetail) {
		// TODO Auto-generated method stub
      	RolePrivilegeEntity rolePrivilegeEntity1 = rolePrivilegeRepository.findByRoleIdAndPrivilegeIdAndIsDeleted(rolePrivilegeDetail.getRoleId(),rolePrivilegeDetail.getPrivilegeId(),false);
		if(rolePrivilegeEntity1==null) {
			RolePrivilegeEntity rolePrivilegeEntity = new RolePrivilegeEntity();
			BeanUtils.copyProperties(rolePrivilegeDetail, rolePrivilegeEntity);
			rolePrivilegeEntity.setPrivilaged(true);
			rolePrivilegeRepository.save(rolePrivilegeEntity);
		}
		
		else {
			if(rolePrivilegeEntity1.isPrivilaged()==true) {
			rolePrivilegeEntity1.setPrivilaged(false);
			rolePrivilegeRepository.save(rolePrivilegeEntity1);
			}
			else {
				rolePrivilegeEntity1.setPrivilaged(true);
				rolePrivilegeRepository.save(rolePrivilegeEntity1);
			}
			
		}

		return "Data added!";
	}

	@Override
	public String updateRolePrivilegeData(RolePrivilegeRequestModel rolePrivilegeDetail, Long rolePrivilegeId) {
		// TODO Auto-generated method stub
		RolePrivilegeEntity rolePrivilegeEntity = rolePrivilegeRepository.findByRolePrivilegeIdAndIsDeleted(rolePrivilegeId,false);
		BeanUtils.copyProperties(rolePrivilegeDetail, rolePrivilegeEntity);
		rolePrivilegeRepository.save(rolePrivilegeEntity);
		return "Data Updated!";
	}

	@Override
	public String deleteRolePrivilageData(Long rolePrivilegeId) {
		// TODO Auto-generated method stub
		
	RolePrivilegeEntity rolePrivilegeEntity = rolePrivilegeRepository.findByRolePrivilegeIdAndIsDeleted(rolePrivilegeId,false);
	if(rolePrivilegeEntity==null) throw new AppException("role privilage relation with this id not exists");
	
	rolePrivilegeEntity.setDeleted(true);
	rolePrivilegeRepository.save(rolePrivilegeEntity);
	return "Data deleted";

}

	@Override
	public List<RolePrivilegesResponseModel> getRolePrivileges(int page, int limit, String searchKey, Long roleId) {
		
		List<RolePrivilegesResponseModel> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<PrivilegeEntity> privilegePage = null;

		if ("".equals(searchKey))
			privilegePage = privilegeRepository.findByIsDeleted(false,pageableRequest);// .findAll(pageableRequest);
		else
			privilegePage = privilegeRepository.findByPrivilegeNameContainingOrPrivilegeUrlContainingAndIsDeleted(searchKey,
					searchKey,false,pageableRequest);// .findAll(pageableRequest);

		List<PrivilegeEntity> privileges = privilegePage.getContent();

		int totalPages = privilegePage.getTotalPages();
		for (PrivilegeEntity privilegeEntity : privileges) {

			RolePrivilegesResponseModel rolePrivilegesResponseModel = new RolePrivilegesResponseModel();
			List<RolePrivilegeEntity> rolePrivilegeEntities = rolePrivilegeRepository.findByRoleIdAndIsDeleted(roleId, false);
			if(rolePrivilegeEntities.stream().anyMatch(item -> item.getPrivilegeId() == privilegeEntity.getPrivilegeId()))
				rolePrivilegesResponseModel.setAllowed(true);
			else
				rolePrivilegesResponseModel.setAllowed(false);
			
			BeanUtils.copyProperties(privilegeEntity, rolePrivilegesResponseModel);
				
			
			UserEntity userEntity = userRepository.findByUserId(privilegeEntity.getCreatedBy());
			
			if (returnValue.size() == 0) {
				rolePrivilegesResponseModel.setTotalPage(totalPages);
			}

			returnValue.add(rolePrivilegesResponseModel);
		}
		
	  return returnValue;
	}

}