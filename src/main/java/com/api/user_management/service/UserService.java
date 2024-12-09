package com.api.user_management.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.api.user_management.shared.dto.UserDto;
import com.api.user_management.ui.model.request.EmailVerificationRequestModel;
import com.api.user_management.ui.model.request.ResetPasswordRequestModel;

public interface UserService {

	UserDto createUser(UserDto user) throws AddressException, MessagingException, IOException;
	UserDto getuser(String email);
	UserDto getUserByUserId(String userId);
	
	//Update user service
	UserDto updateUser(String userId,UserDto userDto);
	
	//delete
	void deleteUser(String userId);
	
	//Get All Service
	UserDto updateUserStatus(String id, UserDto userDto);
	String checkEmail(String email);
	String resetPassword(ResetPasswordRequestModel resetPasswordDetail);
	String changeAccountPassword(ResetPasswordRequestModel changePassRequest);
	UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
	List<UserDto> getUsers(int page, int limit, String userType, String searchKey, boolean isPublic, String department, String role) throws IOException;
	String verifyEmail(EmailVerificationRequestModel requestModel);
	String resendCode(EmailVerificationRequestModel requestModel);
	UserDto updateUserPrivacy(String id, UserDto userDto);

}
