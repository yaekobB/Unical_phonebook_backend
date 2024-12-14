package com.api.user_management.io.repositories;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.ChatMessage;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

	ChatMessage findByMessageIdAndIsDeleted(Long messageId, boolean b);

	ChatMessage findTopBySenderIdOrRecipientIdOrderByTimestampDesc(String userId, String userId2);

	Page<ChatMessage> findByIsDeletedAndContentContainingAndSenderIdAndRecipientIdOrRecipientIdAndSenderId(boolean b,
			String searchKey, String senderId, String recieverId, String recieverId2, String senderId2,
			Pageable pageableRequest);

	Page<ChatMessage> findByIsDeletedAndSenderIdOrRecipientId(boolean b, String senderId, String recieverId,
			Pageable pageableRequest);

	Page<ChatMessage> findByIsDeletedAndSenderIdAndRecipientIdOrRecipientIdAndSenderId(boolean b, String senderId,
			String recieverId, String senderId2, String recieverId2, Pageable pageableRequest);

	ChatMessage findTopBySenderIdAndRecipientIdOrderByTimestampDesc(String userId, String userId2);


	List<ChatMessage> findBySenderIdAndRecipientIdOrderByTimestampDesc(String userId, String recipientId);

	List<ChatMessage> findByIsDeletedAndSenderIdAndRecipientIdOrSenderIdAndRecipientId(boolean b, String recipientId,
			String senderId, String recipientId2, String senderId2);

	ChatMessage findTopByIsDeletedAndSenderIdAndRecipientIdOrSenderIdAndRecipientId(boolean b, String recipientId,
			String senderId, String recipientId2, String senderId2);


}
