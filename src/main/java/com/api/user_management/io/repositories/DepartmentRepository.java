package com.api.user_management.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.DepartmentEntity;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentEntity, Integer> {

	DepartmentEntity findByDepartmentIdAndIsDeleted(Integer departmentId, boolean b);

	Page<DepartmentEntity> findByIsDeleted(boolean b, Pageable pageableRequest);

	Page<DepartmentEntity> findByIsDeletedAndDepartmentNameContaining(boolean b, String searchKey,
			Pageable pageableRequest);

}
