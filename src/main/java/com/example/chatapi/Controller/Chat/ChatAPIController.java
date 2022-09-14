package com.example.chatapi.Controller.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.Service.ChatService;
import com.example.chatapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatAPIController {

    private final UserService userService;
    private final ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDTO>> getChatRoomList() {
        return ResponseEntity.ok(chatService.getChatRoomList());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<Boolean> createChatRoom(Principal principal, @RequestBody ChatRoomDTO chatRoom) {
        log.warn(chatRoom.toString());
        chatService.createChatRoom(principal.getName(), chatRoom);
        return ResponseEntity.ok(true);
    }

}
