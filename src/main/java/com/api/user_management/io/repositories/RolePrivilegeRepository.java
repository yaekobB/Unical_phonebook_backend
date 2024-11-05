package com.api.user_management.io.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.user_management.io.entity.RolePrivilegeEntity;

public interface RolePrivilegeRepository extends PagingAndSortingRepository<RolePrivilegeEntity, Long>{


	RolePrivilegeEntity findByRolePrivilegeIdAndIsDeleted(Long rolePrivilegeId, boolean b);

	List<RolePrivilegeEntity> findByIsDeleted(boolean b);

	RolePrivilegeEntity findByRoleIdAndPrivilegeIdAndIsDeleted(Long roleId, Integer privilegeId, boolean b);

	RolePrivilegeEntity findByRolePrivilegeId(Long rolePrivilegeId);

	List<RolePrivilegeEntity> findByRoleIdAndIsDeleted(Long id, boolean b);

	List<RolePrivilegeEntity> findByRoleIdAndIsDeleted(Integer roleId, boolean b);
	
}
