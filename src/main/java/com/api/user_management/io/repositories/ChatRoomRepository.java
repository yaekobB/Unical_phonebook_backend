package com.api.user_management.io.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>{

	ChatRoomEntity findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(String senderId, String recieverId,
			String senderId2, String recieverId2);

	List<ChatRoomEntity> findBySenderIdOrRecipientId(String userId, String userId2);




}
