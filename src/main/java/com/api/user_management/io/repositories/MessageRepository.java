package com.api.user_management.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

	MessageEntity findByMessageIdAndIsDeleted(Long messageId, boolean b);


	Page<MessageEntity> findByIsDeletedAndContentContainingAndSenderIdOrRecieverId(boolean b, String searchKey,
			Long userId, Long userId2, Pageable pageableRequest);

	Page<MessageEntity> findByIsDeletedAndSenderIdOrRecieverId(boolean b, Long userId, Long userId2,
			Pageable pageableRequest);


	MessageEntity findTopBySenderIdOrRecieverIdOrderByTimestampDesc(Long userId, Long userId2);


	Page<MessageEntity> findByIsDeletedAndContentContainingAndSenderIdAndRecieverIdOrRecieverIdAndSenderId(boolean b,
			String searchKey, Long senderId, Long recieverId, Long recieverId2, Long senderId2,
			Pageable pageableRequest);

}
