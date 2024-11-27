package com.api.user_management.service;

import java.util.List;

import com.api.user_management.ui.model.request.MessageRequestModel;
import com.api.user_management.ui.model.response.MessageResponseModel;

public interface MessageService {

	MessageResponseModel addMessage(MessageRequestModel messagesDetail);

	MessageResponseModel updateMessage(Long messageId, MessageRequestModel messageDetails);

	MessageResponseModel getMessage(Long messageId);

	List<MessageResponseModel> getMessages(String searchKey, int page, int limit, Long senderId, Long recieverId);

	String deleteMessage(Long messageId);

	List<MessageResponseModel> getChatRoomMessages(String searchKey, int page, int limit, Long userId);

	MessageResponseModel saveChatRoomMessages(MessageRequestModel messagesDetail);

}
