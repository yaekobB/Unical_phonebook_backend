package com.api.user_management.io.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.io.entity.ChatRoom;


@Repository
public interface ChatChatMessageRepository extends JpaRepository<ChatMessage, String> {

}
