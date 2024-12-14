package com.api.user_management.io.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.model.audit.Audit;

@Entity
@Table(name = "chat_message")
public class ChatMessage extends Audit{
	
	private static final long serialVersionUID = -7144799871762972545L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@Column()
    private Long chatRoomId;
	
	@Column()
    private String senderId;
	
	@Column()
    private String recipientId;
	
	@Column()
    private String content;
	
	@Column()
    private LocalDateTime timestamp = LocalDateTime.now();


	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}


	
}
