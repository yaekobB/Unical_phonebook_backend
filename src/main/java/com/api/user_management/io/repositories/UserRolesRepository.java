package com.api.user_management.io.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.UserRolesEntity;

@Repository
public interface UserRolesRepository extends PagingAndSortingRepository<UserRolesEntity, Long> {

	UserRolesEntity findByUserRoleId(Long userRoleId);


	UserRolesEntity findByUserRoleIdAndIsDeleted(Long userRoleId, boolean b);

	List<UserRolesEntity> findByUserId(Long id);

	List<UserRolesEntity> findByRoleId(Long id);
	

}
