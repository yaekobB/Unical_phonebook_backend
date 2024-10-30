package com.api.user_management.service.Impl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.exception.AppException;
import com.api.user_management.io.entity.DepartmentEntity;
import com.api.user_management.io.entity.RoleEntity;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.DepartmentRepository;
import com.api.user_management.io.repositories.RoleRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.UserService;
import com.api.user_management.shared.GenerateRandomString;
import com.api.user_management.shared.SendMail;
import com.api.user_management.shared.dto.UserDto;
import com.api.user_management.ui.model.request.ResetPasswordRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;


@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GenerateRandomString generateRandomString;
	
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    RoleRepository roleRepository;
	
	@Autowired
	SendMail sendMailComponent;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Value("${file.upload-dir}")
    private String uploadDirectory;
	
	@Value("${app.HostDomain}")
    private String applicationHostDomain;
	
	
	public UserDto createUser(UserDto user) throws AddressException, MessagingException, IOException {
		
		//UserEntity checkEmailEntity = userRepository.findByEmail(user.getEmail());
		if(userRepository.findByEmail(user.getEmail()) != null) throw new AppException("Record already exists with this Email.");
				
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
						 
		String defaultUserStatus = "NotVerified";
		String emailVerificationToken = generateRandomString.generateUserId(45);
		
		userEntity.setEmailVerificationToken(emailVerificationToken);
		userEntity.setUserStatus(defaultUserStatus);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(generateRandomString.generateUserId(30));
		
		RoleEntity userRole = roleRepository.findByRoleName(user.getUserType());
		
		if(userRole == null) 
			throw new AppException("User RoleEntity not set.");
		
		userEntity.setRoles(Collections.singleton(userRole));
		UserEntity storedUserDetailsEntity = userRepository.save(userEntity);
		
//		String mailSubject = "Laboratory account Verification";
//		String mailBody = "<b>Verify your boston advanced laboratory </b><br><br> Follow this link --> <a href='" + applicationHostDomain + "/verifyaccount?verificationToken=" + emailVerificationToken + "'><b><i>Click me to Verify</i></b></a><br><br> <span style='color:red; font-size:12px;'> Don't reply to this email !</span>";
//		SendEmailRequestModel sendMail = new SendEmailRequestModel();
//		sendMail.setToAddress(storedUserDetailsEntity.getEmail());
//		sendMail.setSubject(mailSubject);
//		sendMail.setBody(mailBody);
//		String emailStatus = sendMailComponent.sendMail(sendMail);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetailsEntity, returnValue);
		
		return returnValue;
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getuser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new AppException("User not found");
		
		UserDto returnValue = new UserDto();
		DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(userEntity.getDepartmentId(),false);
		if(departmentEntity!=null) {
			BeanUtils.copyProperties(departmentEntity, departmentResponseModel);
			returnValue.setDepartmentResponseModel(departmentResponseModel);	
		}
		BeanUtils.copyProperties(userEntity, returnValue); 
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String UserId) {
			
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(UserId);
		
		if(userEntity == null) throw new AppException("User not found");
		
		BeanUtils.copyProperties(userEntity, returnValue); 
		DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(userEntity.getDepartmentId(),false);
		if(departmentEntity!=null) {
			BeanUtils.copyProperties(departmentEntity, departmentResponseModel);
			returnValue.setDepartmentResponseModel(departmentResponseModel);	
		}

		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new RuntimeException("User not found.");
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setMiddleName(userDto.getMiddleName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPhoneNumber(userDto.getPhoneNumber());
		userEntity.setUserType(userDto.getUserType());
		userEntity.setDepartment(userDto.getDepartment());
		
//		RoleEntity userRole = roleRepository.findByRoleName("Admin");
//		if(userRole == null) 
//			throw new AppException("User RoleEntity not set.");
//		
//		userEntity.setRoles(Collections.singleton(userRole));
		
				
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		
//		RoleEntity roleEntity = roleRepository.findByRoleName(userDto.getUserType());
//		if(roleEntity!= null) {		
//			long Id = userEntity.getId();
//			long roleId = roleEntity.getId();
//			
//			user
//			entityManager.createQuery("UPDATE user_roles set role_id =:roleId WHERE user_id=:Id")
//		     .setParameter("roleId", roleId)
//		     .setParameter("Id", Id)
//		     .executeUpdate();	
		
		
		BeanUtils.copyProperties(updatesUserDetailsEntity, returnValue); 
		return returnValue;
	}
	
	@Override
	public UserDto updateUserStatus(String userId, UserDto userDto) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new RuntimeException("User not found.");
		
		userEntity.setUserStatus(userDto.getUserStatus());		
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatesUserDetailsEntity, returnValue); 
		return returnValue;
	}
	
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new AppException("User not found");
		
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit, String userType) throws IOException{
		 
	    List<UserDto> returnValue = new ArrayList<>();
	    
	    if(page > 0) page = page - 1; 
	   
	    Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("id").descending());
	    Page<UserEntity> usersPage = null;
	    if(userType.equals("All")) {
	    	usersPage = userRepository.findAll(pageableRequest);
	    }else {
	    	usersPage = userRepository.findByUserType(userType,pageableRequest);
	    }
	   
	    
	    int totalPages = usersPage.getTotalPages();
	    List<UserEntity> users = usersPage.getContent();
	    for(UserEntity userEntity : users) {
	    	UserDto userDto = new UserDto(); 
	    	BeanUtils.copyProperties(userEntity, userDto);
			DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
			DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(1,
					false);

			if(departmentEntity!=null) {
				System.out.print("==============================="+departmentEntity.getDepartmentId()+"=====================================");

				BeanUtils.copyProperties(departmentEntity, departmentResponseModel);
				userDto.setDepartmentResponseModel(departmentResponseModel);	
			}
	    	if(returnValue.size() == 0) {
	    		userDto.setTotalPages(totalPages);
	    	}
	    	returnValue.add(userDto);
	    }
	    
		return returnValue;
	}

	@Override
	public String checkEmail(String email) {
		if(userRepository.findByEmail(email) == null) {
			return "Email doesn't exist";
		}else {
			return "Email exists";
		}
	}

	@Override
	public String resetPassword(ResetPasswordRequestModel resetPasswordDetail) {
		
		String returnValue = "Password not changed";
		UserEntity userEntity = userRepository.findByEmailAndPasswordResetCode(resetPasswordDetail.getEmail(),resetPasswordDetail.getResetCode());
		if(userEntity == null) throw new AppException("Password Can't be changed");
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(resetPasswordDetail.getNewPassword()));
		UserEntity passworUpdated = userRepository.save(userEntity);
		if(passworUpdated != null) {
			returnValue = "Password changed successfully";
		}
		return returnValue;
	}

	@Override
	public String changeAccountPassword(ResetPasswordRequestModel changePassRequest) {
		
		UserEntity userEntity = userRepository.findByEmail(changePassRequest.getEmail());
		
		if(userEntity == null) 
			throw new RuntimeException("User not found.");
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(changePassRequest.getNewPassword()));
		userRepository.save(userEntity);
		
		return "Password changed successfully";
	}

}
