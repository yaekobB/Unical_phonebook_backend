package com.api.user_management.service.Impl;
import com.api.user_management.io.entity.ChatMessage;
import com.api.user_management.io.entity.ChatRoom;
import com.api.user_management.io.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
	
	
    private ChatMessageRepository repository;
    private ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        Optional<String> chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                ; // You can create your own dedicated exception
//        chatMessage.setChatRoomId(chatId.get());
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
//        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
        return null;
    }
}
