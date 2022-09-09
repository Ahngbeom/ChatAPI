package com.example.chatapi.Controller.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatAPIController {

    private final ChatService chatService;

    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDTO>> chatList() {
        return ResponseEntity.ok(chatService.getChatRoomList());
    }

}
