package com.api.user_management.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.model.audit.Audit;

@Entity
@Table(name = "chat_roomsss")
public class ChatRoom extends Audit{

	
	@Id
	private String id;

	@Column()
    private String chatId;

	@Column()
    private String senderId;
	
	@Column()
    private String recipientId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
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
	
	
	  // Private constructor to enforce the builder usage
    private ChatRoom(Builder builder) {
        this.chatId = builder.chatId;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
    }

    // Builder class
    public static class Builder {
        private String chatId;
        private String senderId;
        private String recipientId;

        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder recipientId(String recipientId) {
            this.recipientId = recipientId;
            return this;
        }

        public ChatRoom build() {
            return new ChatRoom(this);
        }
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }
}
