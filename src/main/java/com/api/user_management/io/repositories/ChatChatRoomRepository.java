package com.api.user_management.io.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.ChatRoom;

@Repository
public interface ChatChatRoomRepository extends JpaRepository<ChatRoom, String> {

	Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

}
