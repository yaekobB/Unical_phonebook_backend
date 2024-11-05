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
import com.api.user_management.io.entity.DepartmentEntity;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.DepartmentRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.DepartmentService;
import com.api.user_management.ui.model.request.DepartmentRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;


	@Autowired
	UserRepository userRepository;

	@Override
	public DepartmentResponseModel addDepartment(DepartmentRequestModel departmentDetails) {

		DepartmentResponseModel returnValue = new DepartmentResponseModel();
		DepartmentEntity departmentEntity = new DepartmentEntity();
		BeanUtils.copyProperties(departmentDetails, departmentEntity);
//		departmentEntity.setDepartmentFullName("ROLE_" + departmentEntity.getDepartmentName());
		DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
		BeanUtils.copyProperties(savedDepartmentEntity, returnValue);
//		if(savedDepartment != null) {
//			returnValue="Department Added";
//		}
		return returnValue;
	}

	@Override
	public String updateDepartment(Integer departmentId, DepartmentRequestModel departmentDetails) {

		String returnValue = "";

		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(departmentId, false);
		if (departmentEntity == null)
			throw new AppException("User DepartmentEntity not found.");

		BeanUtils.copyProperties(departmentDetails, departmentEntity);
//		departmentEntity.setDepartmentFullName("ROLE_" + departmentDetails.getDepartmentName());
		DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
		if (savedDepartment != null) {
			returnValue = "Department Updated";
		}

		return returnValue;
	}

	@Override
	public String deleteDepartment(Integer departmentId) {
		// TODO Auto-generated method stub
		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(departmentId, false);

		if (departmentEntity == null)
			throw new AppException("Department not found");

		departmentEntity.setDeleted(true);
		departmentRepository.save(departmentEntity);
		return "Data deleted";

	}

	@Override
	public List<DepartmentResponseModel> getDepartments(String searchKey, int page, int limit) {
		List<DepartmentResponseModel> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("departmentName").ascending());
		Page<DepartmentEntity> departmentPage = null;

		if ("".equals(searchKey))
			departmentPage = departmentRepository.findByIsDeleted(false, pageableRequest);// .findAll(pageableRequest);
		else
			departmentPage = departmentRepository.findByIsDeletedAndDepartmentNameContaining(false, searchKey, pageableRequest);// .findAll(pageableRequest);

		List<DepartmentEntity> departmentEntities = departmentPage.getContent();

		int totalPages = departmentPage.getTotalPages();
		for (DepartmentEntity departmentEntity : departmentEntities) {

			DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
			BeanUtils.copyProperties(departmentEntity, departmentResponseModel);

			UserEntity userEntity = userRepository.findByUserId(departmentEntity.getCreatedBy());
			if (userEntity != null)
				departmentResponseModel.setCreatedBy(userEntity.getFirstName() + " " + userEntity.getLastName());
			else
				departmentResponseModel.setCreatedBy("");

			if (returnValue.size() == 0) {
				departmentResponseModel.setTotalPage(totalPages);
			}

			returnValue.add(departmentResponseModel);
		}
		return returnValue;
	}

	@Override
	public DepartmentResponseModel getDepartment(Integer departmentId) {
		// TODO Auto-generated method stub
		DepartmentResponseModel returnValue = new DepartmentResponseModel();
		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(departmentId, false);
		if(departmentEntity==null) throw new AppException("No department with this id");
		BeanUtils.copyProperties(departmentEntity, returnValue);
		returnValue.setCreatedBy(departmentEntity.getCreatedBy());
		return returnValue;
	}

}
