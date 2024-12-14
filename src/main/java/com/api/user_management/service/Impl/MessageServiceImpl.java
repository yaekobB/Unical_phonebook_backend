package com.api.user_management.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.io.entity.ChatRoomEntity;
import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.ChatRoomRepository;
import com.api.user_management.io.repositories.MessageRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.MessageService;
import com.api.user_management.shared.dto.PhonebookNotFoundException;
import com.api.user_management.ui.model.request.DepartmentRequestModel;
import com.api.user_management.ui.model.request.MessageRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;
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
		ChatMessage messageEntity = new ChatMessage();
		BeanUtils.copyProperties(messagesDetail, messageEntity);
		ChatMessage savedChatMessage = messageRepository.save(messageEntity);
		BeanUtils.copyProperties(savedChatMessage, returnValue);
		return returnValue;
	}

	@Override
	public ChatMessage updateMessage(Long messageId, ChatMessage messageDetails) {
		ChatMessage returnValue = new ChatMessage();
		ChatMessage messageEntity = messageRepository.findByMessageIdAndIsDeleted(messageId, false);
		if (messageEntity == null)
			new PhonebookNotFoundException("Message Not Found");
		BeanUtils.copyProperties(messageDetails, messageEntity);
		ChatMessage savedChatMessage = messageRepository.save(messageEntity);
		BeanUtils.copyProperties(savedChatMessage, returnValue);
		return returnValue;
	}

	@Override
	public List<MessageResponseModel> getMessages(String searchKey, int page, int limit, String senderId,
			String recieverId) {
		List<MessageResponseModel> returnValue = new ArrayList<>();
//System
		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("messageId").ascending());
		Page<ChatMessage> messagePage = null;

		ChatRoomEntity chatRoomEntity = chatRoomRepository.findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(senderId,
				recieverId, senderId, recieverId);
//		System.out.print("==========brhane teamrat========="+chatRoomEntity.getRecipientId()+"=======================");
		
		if (chatRoomEntity == null) {
			ChatMessage messageRequestModel = new ChatMessage();
			messageRequestModel.setSenderId(senderId);
			messageRequestModel.setRecipientId(recieverId);
			MessageResponseModel chatMessage=	this.saveChatRoomMessages(messageRequestModel);
			returnValue.add(chatMessage);
		   return returnValue;
		}

		else {
		
			if ("".equals(searchKey))
				messagePage = messageRepository.
				findByIsDeletedAndSenderIdAndRecipientIdOrRecipientIdAndSenderId(false, 
						senderId, recieverId,senderId, recieverId,
						pageableRequest);// .findAll(pageableRequest);
			else
				messagePage = messageRepository.findByIsDeletedAndContentContainingAndSenderIdAndRecipientIdOrRecipientIdAndSenderId(
						false, searchKey, senderId, recieverId,senderId,recieverId, pageableRequest);// .findAll(pageableRequest);
			List<ChatMessage> messageEntities = messagePage.getContent();

			int totalPages = messagePage.getTotalPages();
			for (ChatMessage messageEntity : messageEntities) {

				MessageResponseModel messageResponseModel = new MessageResponseModel();
				BeanUtils.copyProperties(messageEntity, messageResponseModel);

				UserEntity userEntity = userRepository.findByUserId(senderId);
				if (userEntity.getUserId().equals(messageEntity.getRecipientId())) {
					messageResponseModel.setSender(false);
				} else {
					messageResponseModel.setSender(true);
					userEntity = userRepository.findByUserId(recieverId);

				}
				
//				messageResponseModel.setContent(messageEntity.getContent());
				messageResponseModel.setCreatedBy(userEntity.getFirstName() + " " + userEntity.getMiddleName()
						+ " " + userEntity.getLastName());
				messageResponseModel.setCreaterId(userEntity.getUserId());

//			if (returnValue.size() == 0) {
//				messageResponseModel.setTotalPage(totalPages);
//			}

				returnValue.add(messageResponseModel);
			}
		}
		return returnValue;
	}

	@Override
	public ChatMessage getMessage(Long messageId) {
		
		ChatMessage returnValue = new ChatMessage();
		ChatMessage messageEntity = messageRepository.findByMessageIdAndIsDeleted(messageId, false);
		if (messageEntity == null)
			throw new PhonebookNotFoundException("Message not found");
		BeanUtils.copyProperties(messageEntity, returnValue);
		return returnValue;
	}

	@Override
	public String deleteMessage(Long messageId) {
		ChatMessage messageEntity = messageRepository.findByMessageIdAndIsDeleted(messageId, false);
		if (messageEntity == null)
			throw new PhonebookNotFoundException("Message not found");
		messageEntity.setDeleted(true);
		messageRepository.save(messageEntity);
		return "Deleted";
	}

	@Override
	public List<MessageResponseModel> getChatRoomMessages(String searchKey, int page, int limit, 
			String userId) {

		List<MessageResponseModel> returnValue = new ArrayList<MessageResponseModel>();
		List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.
				findBySenderIdOrRecipientId(userId.trim(), userId.trim());

		for (ChatRoomEntity chatRoomEntity : chatRoomEntities) {
			MessageResponseModel messageResponseModel = new MessageResponseModel();
			BeanUtils.copyProperties(chatRoomEntity, messageResponseModel);
			UserEntity userEntity = userRepository.findByUserId(userId);
			
			if (chatRoomEntity!=null&& userEntity!=null&& userEntity.getUserId().equals(chatRoomEntity.getRecipientId())) {
				messageResponseModel.setSender(false);
			} else {
				messageResponseModel.setSender(true);
			}

			ChatMessage chatMessages12 = messageRepository.findTopByIsDeletedAndSenderIdAndRecipientIdOrSenderIdAndRecipientId(false,
					chatRoomEntity.getRecipientId(), chatRoomEntity.getSenderId(),chatRoomEntity.getRecipientId()
					,chatRoomEntity.getSenderId() );
			
			UserEntity userEntity2=null;
			
			if(chatMessages12!=null) {
				messageResponseModel.setContent(chatMessages12.getContent());
				messageResponseModel.setCreatedAt(chatMessages12.getCreatedAt());

			
			}
			if(chatRoomEntity.getSenderId().equals(userId)) {

				userEntity2=userRepository.findByUserId(chatRoomEntity.getRecipientId());
				messageResponseModel.setCreatedBy(userEntity2.getFirstName() + " " + userEntity2.getMiddleName()
				+ " " + userEntity2.getLastName());
				messageResponseModel.setCreaterId(userEntity2.getUserId());
			}
			else if(!chatRoomEntity.getSenderId().equals(userId)) {
				userEntity2=userRepository.findByUserId(chatRoomEntity.getSenderId());
				messageResponseModel.setCreatedBy(userEntity2.getFirstName() + " " + userEntity2.getMiddleName()
				+ " " + userEntity2.getLastName());
				messageResponseModel.setCreaterId(userEntity2.getUserId());

			}
			
           if(!chatRoomEntity.getSenderId().equals(chatRoomEntity.getRecipientId()))
			returnValue.add(messageResponseModel);
		}
		Collections.sort(returnValue, (a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
//		returnValue.sort(Comparator.comparing(entity -> entity).reversed());

		return returnValue;
	}

	@Override
	public MessageResponseModel saveChatRoomMessages(ChatMessage messagesDetail) {
		MessageResponseModel returnValue = new MessageResponseModel();
		ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
		BeanUtils.copyProperties(messagesDetail, chatRoomEntity);
		ChatRoomEntity chatRoomEntity2 = chatRoomRepository.save(chatRoomEntity);
		BeanUtils.copyProperties(chatRoomEntity2, returnValue);
		return returnValue;
	}



	@Override
	public DepartmentResponseModel addDepartment(DepartmentRequestModel departmentsDetail) {
		// TODO Auto-generated method stub
		return null;
	}

}
