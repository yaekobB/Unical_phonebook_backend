package com.api.user_management.service.Impl;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.api.exception.AppException;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.AccountVerificationService;
import com.api.user_management.shared.GenerateRandomString;
import com.api.user_management.shared.SendMail;
import com.api.user_management.ui.model.request.ResetPasswordRequestModel;
import com.api.user_management.ui.model.request.SendEmailRequestModel;

@Service
@Transactional
public class AccountVerificationServiceImpl implements AccountVerificationService {
	
	@Autowired
	SendMail sendMailComponent;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GenerateRandomString generateRandomString;
	
	@Value("${app.HostDomain}")
    private String applicationHostDomain;
	
	@Override
	public String verifyAccount(String emailVerificationToken) {
		String returnValue = "";
		String userStatus = "Active";
		UserEntity userEntity = userRepository.findByEmailVerificationToken(emailVerificationToken);
		if(userEntity == null) throw new AppException("User not found");
		
		userEntity.setUserStatus(userStatus);
		UserEntity updatedUserEntity = userRepository.save(userEntity);
		if(updatedUserEntity.getUserStatus() == "Active") {
			returnValue = "Account Verified Successfully";
		}
		return returnValue;
	}

	@Override
	public String sendPasswordResetCode(String email) throws AddressException, MessagingException, IOException {
		String returnValue = "";
		String resetCode = generateRandomString.generateAccountId(6);
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new AppException("User not found");
		
		userEntity.setPasswordResetCode(resetCode);
		userRepository.save(userEntity);
		
		String mailSubject = "Laboratory account Password Reset";
		String mailBody = "<b>Reset your Laboratory Account Password</b><br><br> Enter the code or Follow the link <br> Reset Code : <b>" + resetCode + "</b> <br> <a href='" + applicationHostDomain + "/resetpassword?resetCode=" + resetCode + "'><b><i>Click me to Reset Password</i></b></a><br><br> <span style='color:red; font-size:12px;'> Don't reply to this email !</span>";
		SendEmailRequestModel sendMail = new SendEmailRequestModel();
		sendMail.setToAddress(email);
		sendMail.setSubject(mailSubject);
		sendMail.setBody(mailBody);
//		String emailStatus = sendMailComponent.sendMail(sendMail);
		//String returnValue = sendMailcomponent.sendMail(email);
		return "Password Reset Code sent";
	}

	@Override
	public String checkResetCode(ResetPasswordRequestModel resetPasswordDetail) {
		
		UserEntity userEntity = userRepository.findByEmailAndPasswordResetCode(resetPasswordDetail.getEmail(),resetPasswordDetail.getResetCode());
		if(userEntity == null) throw new AppException("Invalid Reset Code");
		
		return "Reset Code is valid";
	}

	@Override
	public String reSendVerification(String email) throws AddressException, MessagingException, IOException {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new AppException("User not registered");

		String mailSubject = "Laboratory account Verification";
		String mailBody = "<b>Verify your Laboratory Account</b><br><br> Follow this link --> <a href='" + applicationHostDomain + "/verifyaccount?verificationToken=" + userEntity.getEmailVerificationToken() + "'><b><i>Click me to Verify</i></b></a><br><br> <span style='color:red; font-size:12px;'> Don't reply to this email !</span>";
		SendEmailRequestModel sendMail = new SendEmailRequestModel();
		sendMail.setToAddress(email);
		sendMail.setSubject(mailSubject);
		sendMail.setBody(mailBody);
//		String returnValue = sendMailComponent.sendMail(sendMail);
		
		return "";
	}

	

}
