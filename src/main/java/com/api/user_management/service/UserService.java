package com.api.user_management.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.api.user_management.shared.dto.UserDto;
import com.api.user_management.ui.model.request.ResetPasswordRequestModel;
public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user) throws AddressException, MessagingException, IOException;
	UserDto getuser(String email);
	UserDto getUserByUserId(String userId);
	
	//Update user service
	UserDto updateUser(String userId,UserDto userDto);
	void deleteUser(String userId);
	
	//Get All Service
	List<UserDto> getUsers(int page, int limit, String userType) throws AddressException, MessagingException, IOException;
	UserDto updateUserStatus(String id, UserDto userDto);
	String checkEmail(String email);
	String resetPassword(ResetPasswordRequestModel resetPasswordDetail);
	String changeAccountPassword(ResetPasswordRequestModel changePassRequest);

	
}
