package com.api.user_management.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.RoleEntity;
import com.api.user_management.shared.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Long> {
//    Optional<RoleEntity> findByName(RoleName roleUser);

	RoleEntity findByRoleName(String userRole);

	RoleEntity findByIdAndIsDeleted(long roleId, boolean b);

	Page<RoleEntity> findByIsDeleted(boolean b, Pageable pageableRequest);

	Page<RoleEntity> findByIsDeletedAndRoleNameContaining(boolean b, String searchKey, Pageable pageableRequest);

}
