package com.api.user_management.ui.model.response;

import com.api.model.audit.Audit;

public class MessageResponseModel extends Audit {

	private Long chatRoomId;
	private String senderId;
	private String recipientId;
	private String createrId;
	private String content;
	

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	private boolean isSender;
	
	public boolean isSender() {
		return isSender;
	}
	public void setSender(boolean isSender) {
		this.isSender = isSender;
	}

	
}
