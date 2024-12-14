package com.api.user_management.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ChatNotification {
    private Long chatRoomId;
    private String senderId;
    private String recipientId;
    private String content;
    
    
	public ChatNotification(Long chatRoomId, String senderId, String recipientId, String content) {
		super();
		this.chatRoomId = chatRoomId;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.content = content;
	}
	
	
	public ChatNotification() {
		super();
		// TODO Auto-generated constructor stub
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
    
}
