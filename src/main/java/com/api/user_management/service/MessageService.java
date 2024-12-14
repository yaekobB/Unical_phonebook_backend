package com.api.user_management.service;

import java.util.List;

import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.ui.model.request.DepartmentRequestModel;
import com.api.user_management.ui.model.request.MessageRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;
import com.api.user_management.ui.model.response.MessageResponseModel;

public interface MessageService {

	MessageResponseModel addMessage(MessageRequestModel chatMessage);

	ChatMessage updateMessage(Long messageId, ChatMessage messageDetails);

	ChatMessage getMessage(Long messageId);

	List<MessageResponseModel> getMessages(String searchKey, int page, int limit, String senderId, String recipientId);

	String deleteMessage(Long messageId);

	List<MessageResponseModel> getChatRoomMessages(String searchKey, int page, int limit, String userId);

	MessageResponseModel saveChatRoomMessages(ChatMessage messagesDetail);

	DepartmentResponseModel addDepartment(DepartmentRequestModel departmentsDetail);

}
