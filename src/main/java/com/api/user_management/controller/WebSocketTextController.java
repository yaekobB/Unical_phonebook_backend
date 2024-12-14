package com.api.user_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.user_management.shared.dto.TextMessageDTO;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class WebSocketTextController {
	
	@Autowired
    	SimpMessagingTemplate template;
	
	@PostMapping("/send")
	public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) {
		template.convertAndSend("/topic/greetings", textMessageDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@MessageMapping("/sendMessage")
	public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
		// receive message from client
	}
	
	
	@SendTo("/topic/greetings")
	public String broadcastMessage(@Payload String orderResponseModel) {
		System.out.print("Connectedddddddddddddddddddddddddddd!");
		return orderResponseModel;
	}
}