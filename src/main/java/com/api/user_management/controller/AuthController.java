package com.api.user_management.controller;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.exception.AppException;
import com.api.user_management.security.JwtTokenProvider;
import com.api.user_management.service.UserService;
import com.api.user_management.shared.dto.UserDto;
import com.api.user_management.ui.model.request.LoginRequestModel;
import com.api.user_management.ui.model.request.ResetPasswordRequestModel;
import com.api.user_management.ui.model.request.UserDetailRequestModel;
import com.api.user_management.ui.model.response.JwtAuthenticationResponse;
import com.api.user_management.ui.model.response.UserRest;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend origin

public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
	UserService userService;

    @PostMapping("/login")	
    public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequestModel loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthenticationResponse returnValue = tokenProvider.generateToken(authentication);
        return returnValue;
    }




    @Valid
    @PostMapping(path="/signup")
	public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) throws AddressException, MessagingException, IOException {
		
		UserRest returnValue= new UserRest();
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
    
    @PutMapping(path="/changepassword")
	public String changeAccountPassword(@RequestBody ResetPasswordRequestModel changePassRequest) {
    	
    	String returnValue = "Password not Changed";
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		changePassRequest.getEmail(),
                		changePassRequest.getOldPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthenticationResponse authenticated = tokenProvider.generateToken(authentication);
		
        if(authenticated != null) {
        	 returnValue = userService.changeAccountPassword(changePassRequest);
        }
		
		return returnValue;
	}
}
