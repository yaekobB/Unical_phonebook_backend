package com.api.user_management.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.user_management.service.MessageService;
import com.api.user_management.shared.ChatNotification;
import com.api.user_management.shared.dto.TextMessageDTO;
import com.api.user_management.ui.model.request.MessageRequestModel;
import com.api.user_management.ui.model.response.MessageResponseModel;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/chats")

public class ChatController {

	@Autowired
   SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    MessageService messagesService;

	@PostMapping("/send")
	public ResponseEntity<Void> sendMessage(@RequestBody MessageRequestModel chatMessage) {
		MessageResponseModel savedMsg = messagesService.addMessage(chatMessage);
		TextMessageDTO textMessageDTO = new TextMessageDTO();
		textMessageDTO.setMessage(savedMsg.getContent());
		messagingTemplate.convertAndSend("/topic/greetings", savedMsg);
		return new ResponseEntity<>(HttpStatus.OK);
	}
//	@PostMapping
//	public void addMessage(@RequestBody MessageRequestModel chatMessage) {
//		MessageResponseModel savedMsg = messagesService.addMessage(chatMessage);
//		System.out.print("=====saved====="+savedMsg.getContent()+"==========");
//		MessageResponseModel retMessageResponseModel = new MessageResponseModel();
//		retMessageResponseModel.setContent("savedMsg");
//      messagingTemplate.convertAndSend("topic/greetings",retMessageResponseModel
//      new ChatNotification(
//              savedMsg.getChatRoomId(),
//              savedMsg.getSenderId(),
//              savedMsg.getRecipientId(),
//              savedMsg.getContent()
//      )
//);
//		DepartmentResponseModel returnValue = messagesService.addDepartment(departmentsDetail);
		
//		return null;
		
//	}
//    @PostMapping
//    public void processMessage(@RequestBody MessageRequestModel chatMessage) {
//		System.out.print("========getContent====="+chatMessage.getContent()+"===========");
//
//    	ChatMessage savedMsg = messagesService.addMessageWWWW(chatMessage);
//
//        messagingTemplate.convertAndSend("topic/greetings",
//                new ChatNotification(
//                        savedMsg.getChatRoomId(),
//                        savedMsg.getSenderId(),
//                        savedMsg.getRecipientId(),
//                        savedMsg.getContent()
//                )
//        );
//    }

	@SendTo("/topic/greetings")
	public MessageResponseModel broadcastMessage(
			@Payload MessageResponseModel orderResponseModel) {
		return orderResponseModel;
	}

//	@SendTo("/topic/greetings")
//	public String eeee(@Payload String orderResponseModel) {
//		return orderResponseModel;
//	}
}
