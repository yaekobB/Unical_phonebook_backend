package com.api.user_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.service.Impl.ChatUserService;

import lombok.RequiredArgsConstructor;




@Controller
@RequiredArgsConstructor
public class ChatUserController {

    private ChatUserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserEntity addUser(
            @Payload UserEntity user
    ) {
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserEntity disconnectUser(
            @Payload UserEntity user
    ) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}