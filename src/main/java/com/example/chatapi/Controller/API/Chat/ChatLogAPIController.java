package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.STOMP.Message;
import com.example.chatapi.Service.Chat.ChatLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ChatLogAPIController {

    private final ChatLogService chatLogService;

    @GetMapping("/log/{roomId}")
    public ResponseEntity<List<Message>> getChatRoomLog(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatLogService.getChatRoomLog(roomId));
    }

}
