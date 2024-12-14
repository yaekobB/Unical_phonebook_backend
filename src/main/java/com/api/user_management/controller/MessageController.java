package com.api.user_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.service.MessageService;
import com.api.user_management.ui.model.request.MessageRequestModel;
import com.api.user_management.ui.model.response.MessageResponseModel;

@RestController
@RequestMapping("message")
public class MessageController {

	@Autowired
	MessageService messagesService;
	
	@PostMapping

	public MessageResponseModel saveMessages(@RequestBody MessageRequestModel messagesDetail) {
		
		MessageResponseModel returnValue =null;
		
		return returnValue;
		
	}
	@PutMapping(path = "/{messageId}")
	public ChatMessage updateMessages(@RequestBody ChatMessage messageDetails, @PathVariable Long messageId) {
		ChatMessage returnValue = messagesService.updateMessage(messageId, messageDetails);
		return returnValue;
	}
	
	@GetMapping(path = "/{messageId}")
	public ChatMessage getMessage(@PathVariable Long messageId) {
		ChatMessage returnValue = messagesService.getMessage(messageId);
		return returnValue;
	}
	
	@GetMapping
	public List<MessageResponseModel> getMessages(
			@RequestParam(value="searchKey", defaultValue = "") String searchKey, 
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="limit", defaultValue = "100000000") int limit,
			@RequestParam(value="senderId", defaultValue = "") String senderId,			
			@RequestParam(value="recipientId", defaultValue = "") String recipientId
			) {
		List<MessageResponseModel> returnValue = messagesService.getMessages(searchKey,page,limit,senderId, recipientId);
		return returnValue;
	}

	@DeleteMapping(path = "/{messageId}")
	public String deleteMessage(@PathVariable Long messageId) {
		String returnValue = messagesService.deleteMessage(messageId);
	    return returnValue;
	}
	
	@GetMapping(path = "/chat-room")
	public List<MessageResponseModel> getChatRoomMessages(
			@RequestParam(value="searchKey", defaultValue = "") String searchKey, 
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="limit", defaultValue = "1000000") int limit,
			@RequestParam(value="userId", defaultValue = "") String userId) {
		List<MessageResponseModel> returnValue = messagesService.getChatRoomMessages(searchKey,page,limit,userId);
		return returnValue;
	}
	
	@PostMapping(path = "/chat-room")
	public MessageResponseModel saveChatRoomMessages(@RequestBody ChatMessage messagesDetail) {
		
		MessageResponseModel returnValue = messagesService.saveChatRoomMessages(messagesDetail);
		
		return returnValue;
		
	}	
	
}
