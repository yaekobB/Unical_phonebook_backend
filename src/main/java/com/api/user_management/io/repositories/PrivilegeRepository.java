package com.api.user_management.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.PrivilegeEntity;


@Repository
public interface PrivilegeRepository extends PagingAndSortingRepository<PrivilegeEntity, Integer> {


	PrivilegeEntity findByPrivilegeIdAndIsDeleted(Integer privilegeId, boolean b);

	

	Page<PrivilegeEntity> findByIsDeleted(boolean b, Pageable pageableRequest);

	Page<PrivilegeEntity> findByPrivilegeNameContainingOrPrivilegeUrlContainingAndIsDeleted(String searchKey,
			String searchKey2, boolean b, Pageable pageableRequest);

	PrivilegeEntity findByIsDeleted(boolean b);



	List<PrivilegeEntity> findByPrivilegeUrlAndMethodAndIsDeleted(String privilegeUrl, String method, boolean b);

}
