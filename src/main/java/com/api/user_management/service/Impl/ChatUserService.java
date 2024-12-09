package com.api.user_management.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.ChatUserRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.shared.MessageStatus;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatUserService {

	
	private ChatUserRepository chatUserRepository;
	private UserRepository userRepository;
	
	public void saveUser(UserEntity userEntity) {
	
		userEntity.setStatus(MessageStatus.ONLINE);
		chatUserRepository.save(userEntity);
	}
	
	public void disconnect(UserEntity userEntity) {
		
		Optional<UserEntity> userEntity2 = userRepository.findById(userEntity.getId());
		if(userEntity2.get()!=null) {
			userEntity2.get().setStatus(MessageStatus.OFFLINE);
			userRepository.save(userEntity2.get());
		}
	}
	
	public List<UserEntity> findConnectedUsers(){
		
		return chatUserRepository.findAll();
	}
}
