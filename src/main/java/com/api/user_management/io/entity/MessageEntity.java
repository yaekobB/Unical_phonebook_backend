package com.api.user_management.io.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.api.model.audit.Audit;

@Entity(name = "message")
public class MessageEntity extends Audit{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3448847705397933109L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private Long senderId;
    private Long recieverId;
    private Long chatRoomId;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();

	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
