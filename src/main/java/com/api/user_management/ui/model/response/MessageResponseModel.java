package com.api.user_management.ui.model.response;

import com.api.model.audit.Audit;

public class MessageResponseModel extends Audit {

	private Long senderId;
	private Long recieverId;
	private String content;
	private boolean isSender;
	
	public boolean isSender() {
		return isSender;
	}
	public void setSender(boolean isSender) {
		this.isSender = isSender;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getRecieverId() {
		return recieverId;
	}
	public void setRecieverId(Long recieverId) {
		this.recieverId = recieverId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
