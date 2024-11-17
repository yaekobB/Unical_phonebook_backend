package com.api.user_management.ui.model.request;

public class EmailVerificationRequestModel {

	private Integer verificationCode;
	private String email;
	public Integer getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(Integer verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
