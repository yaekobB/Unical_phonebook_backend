package com.api.user_management.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.user_management.io.entity.ChatRoomEntity;
import com.api.user_management.io.entity.MessageEntity;
import com.api.user_management.io.entity.MessageEntity;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.ChatRoomRepository;
import com.api.user_management.io.repositories.MessageRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.MessageService;
import com.api.user_management.shared.dto.PhonebookNotFoundException;
import com.api.user_management.ui.model.request.MessageRequestModel;
import com.api.user_management.ui.model.response.MessageResponseModel;
import com.api.user_management.ui.model.response.MessageResponseModel;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public MessageResponseModel addMessage(MessageRequestModel messagesDetail) {
		MessageResponseModel returnValue = new MessageResponseModel();
		MessageEntity messageEntity = new MessageEntity();
		BeanUtils.copyProperties(messagesDetail, messageEntity);
		MessageEntity savedMessageEntity = messageRepository.save(messageEntity);
		BeanUtils.copyProperties(savedMessageEntity, returnValue);
		return returnValue;
	}

	@Override
	public MessageResponseModel updateMessage(Long messageId, MessageRequestModel messageDetails) {
		MessageResponseModel returnValue = new MessageResponseModel();
		MessageEntity messageEntity = messageRepository.findByMessageIdAndIsDeleted(messageId, false);
		if (messageEntity == null)
			new PhonebookNotFoundException("Message Not Found");
		BeanUtils.copyProperties(messageDetails, messageEntity);
		MessageEntity savedMessageEntity = messageRepository.save(messageEntity);
		BeanUtils.copyProperties(savedMessageEntity, returnValue);
		return returnValue;
	}

	@Override
	public List<MessageResponseModel> getMessages(String searchKey, int page, int limit, Long senderId,
			Long recieverId) {
		List<MessageResponseModel> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("messageId").ascending());
		Page<MessageEntity> messagePage = null;

		ChatRoomEntity chatRoomEntity = chatRoomRepository.findBySenderIdAndRecieverIdOrRecieverIdAndSenderId(senderId,
				recieverId, senderId, recieverId);
		if (chatRoomEntity == null) {
			MessageRequestModel messageRequestModel = new MessageRequestModel();
			messageRequestModel.setSenderId(senderId);
			messageRequestModel.setRecieverId(recieverId);
			this.saveChatRoomMessages(messageRequestModel);
			return null;
		}

		else {
			if ("".equals(searchKey))
				messagePage = messageRepository.findByIsDeletedAndSenderIdOrRecieverId(false, senderId, recieverId,
						pageableRequest);// .findAll(pageableRequest);
			else
				messagePage = messageRepository.findByIsDeletedAndContentContainingAndSenderIdAndRecieverIdOrRecieverIdAndSenderId(
						false, searchKey, senderId, recieverId,recieverId,senderId, pageableRequest);// .findAll(pageableRequest);
			List<MessageEntity> messageEntities = messagePage.getContent();

			int totalPages = messagePage.getTotalPages();
			for (MessageEntity messageEntity : messageEntities) {

				MessageResponseModel messageResponseModel = new MessageResponseModel();
				BeanUtils.copyProperties(messageEntity, messageResponseModel);

				Optional<UserEntity> userEntity = userRepository.findById(senderId);
				if (userEntity.get().getId() == messageEntity.getRecieverId()) {
					messageResponseModel.setSender(false);
				} else {
					messageResponseModel.setSender(true);
				}
				
//				messageResponseModel.setContent(messageEntity.getContent());
				messageResponseModel.setCreatedBy(userEntity.get().getFirstName() + " " + userEntity.get().getMiddleName()
						+ " " + userEntity.get().getLastName());

//			if (returnValue.size() == 0) {
//				messageResponseModel.setTotalPage(totalPages);
//			}

				returnValue.add(messageResponseModel);
			}
		}
		return returnValue;
	}

	@Override
	public MessageResponseModel getMessage(Long messageId) {

		
		
		
		MessageResponseModel returnValue = new MessageResponseModel();
		MessageEntity messageEntity = messageRepository.findByMessageIdAndIsDeleted(messageId, false);
		if (messageEntity == null)
			throw new PhonebookNotFoundException("Message not found");
		BeanUtils.copyProperties(messageEntity, returnValue);
		return returnValue;
	}

	@Override
	public String deleteMessage(Long messageId) {
		MessageEntity messageEntity = messageRepository.findByMessageIdAndIsDeleted(messageId, false);
		if (messageEntity == null)
			throw new PhonebookNotFoundException("Message not found");
		messageEntity.setDeleted(true);
		messageRepository.save(messageEntity);
		return "Deleted";
	}

	@Override
	public List<MessageResponseModel> getChatRoomMessages(String searchKey, int page, int limit, Long userId) {

		List<MessageResponseModel> returnValue = new ArrayList<MessageResponseModel>();
		List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findBySenderIdOrRecieverId(userId, userId);
		for (ChatRoomEntity chatRoomEntity : chatRoomEntities) {
			MessageResponseModel messageResponseModel = new MessageResponseModel();
			BeanUtils.copyProperties(chatRoomEntity, messageResponseModel);
			Optional<UserEntity> userEntity = userRepository.findById(userId);
			if (userEntity.get().getId() == chatRoomEntity.getRecieverId()) {
				messageResponseModel.setSender(false);
			} else {
				messageResponseModel.setSender(true);
			}

			MessageEntity messageEntity = messageRepository.findTopBySenderIdOrRecieverIdOrderByTimestampDesc(userId,
					userId);
			messageResponseModel.setContent(messageEntity.getContent());
			messageResponseModel.setCreatedBy(userEntity.get().getFirstName() + " " + userEntity.get().getMiddleName()
					+ " " + userEntity.get().getLastName());
			returnValue.add(messageResponseModel);
		}
		return returnValue;
	}

	@Override
	public MessageResponseModel saveChatRoomMessages(MessageRequestModel messagesDetail) {
		MessageResponseModel returnValue = new MessageResponseModel();
		ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
		BeanUtils.copyProperties(messagesDetail, chatRoomEntity);
		ChatRoomEntity chatRoomEntity2 = chatRoomRepository.save(chatRoomEntity);
		BeanUtils.copyProperties(chatRoomEntity2, returnValue);
		return returnValue;
	}

}
