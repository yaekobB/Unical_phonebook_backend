package com.api.user_management.service.Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.api.user_management.io.entity.ChatRoom;
import com.api.user_management.io.repositories.ChatChatRoomRepository;
import com.api.user_management.io.repositories.UserRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private ChatChatRoomRepository chatRoomRepository;
    private UserRepository userRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
    	
    	Optional<String> chatId = chatRoomRepository
    		    .findBySenderIdAndRecipientId(senderId, recipientId)
    		    .map(ChatRoom::getChatId);

    		if (!chatId.isPresent() && createNewRoomIfNotExists) {
    		    String newChatId = createChatId(senderId, recipientId);
    		    chatId = Optional.of(newChatId);
    		}

    		return chatId;
      }

      private String createChatId(String senderId, String recipientId) {
          String chatId = String.format("%s_%s", senderId, recipientId);

          ChatRoom senderRecipient = ChatRoom
                  .builder()
                  .chatId(chatId)
                  .senderId(senderId)
                  .recipientId(recipientId)
                  .build();

          ChatRoom recipientSender = ChatRoom
                  .builder()
                  .chatId(chatId)
                  .senderId(recipientId)
                  .recipientId(senderId)
                  .build();

          chatRoomRepository.save(senderRecipient);
          chatRoomRepository.save(recipientSender);

          return chatId;
    }
}
