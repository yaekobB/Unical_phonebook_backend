package com.api.user_management.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user_management.security.AuthorizeRequest;
import com.api.user_management.service.UserService;
import com.api.user_management.shared.dto.UserDto;
import com.api.user_management.ui.model.request.UserDetailRequestModel;
import com.api.user_management.ui.model.response.OperationStatusModel;
import com.api.user_management.ui.model.response.RequestOperationName;
import com.api.user_management.ui.model.response.RequestOperationStatus;
import com.api.user_management.ui.model.response.UserRest;

@CrossOrigin(origins = "http://localhost:3000") // Allow frontend origin

@RestController
@RequestMapping("/user") //http://localhost:8080/users
public class UserControllers {
	
	@Autowired
	UserService userService;
	
	AuthorizeRequest authObject = new AuthorizeRequest();
	
	@GetMapping(path="/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue= new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@GetMapping(path="/checkemail/{email}")
	public String checkEmail(@PathVariable String email) {
		String returnValue = userService.checkEmail(email);
		return returnValue;
		
	}
	
	//Get All Api
	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value="userType", defaultValue = "All") String userType, 
			@RequestParam(value="page", defaultValue = "1") int page,	   
			@RequestParam(value="limit", defaultValue = "25") int limit) 
					throws AddressException, MessagingException, IOException{
		
//		authObject.authorization();
		List<UserRest> returnValue = new ArrayList<>();
		
		List<UserDto> users= userService.getUsers(page,limit,userType);
		
		for(UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
			
		}
		
		return returnValue;
	}

	
	//Update user api
	@PutMapping(path="/{id}")
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {
		
		UserRest returnValue= new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping(path="changestatus/{id}")
	public UserRest updateUserStatus(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {
		
		UserRest returnValue= new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.updateUserStatus(id, userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		 
		return returnValue;
	}
	
	//Delete
	@DeleteMapping(path="/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOpreationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOpreationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}
	
	
}
