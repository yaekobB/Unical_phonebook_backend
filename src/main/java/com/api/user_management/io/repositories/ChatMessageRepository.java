package com.api.user_management.io.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.io.entity.ChatRoom;



@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    List<ChatMessage> findByChatId(String chatId);

}
