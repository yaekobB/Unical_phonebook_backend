package com.api.user_management.io.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>{

	List<ChatRoomEntity> findBySenderIdOrRecieverId(Long userId, Long userId2);

	ChatRoomEntity findBySenderIdAndRecieverIdOrRecieverIdAndSenderId(Long senderId, Long recieverId, Long senderId2,
			Long recieverId2);


}
