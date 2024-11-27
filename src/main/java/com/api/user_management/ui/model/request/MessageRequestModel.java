package com.api.user_management.ui.model.request;

public class MessageRequestModel {

	
    private Long senderId;
    private Long recieverId;
    private Long chatRoomId;
    private String content;
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
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
    
}
