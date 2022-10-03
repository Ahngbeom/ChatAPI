package com.example.chatapi.Controller.API.Chat;

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
@RequestMapping("/api/chat")
public class ChatAPIController {

    private final UserService userService;
    private final ChatService chatService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listOfAll")
    public ResponseEntity<List<ChatRoomDTO>> getListOfAllChatRooms() {
        return ResponseEntity.ok(chatService.getListOfAllChatRooms());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDTO>> getListOfAllChatRoomsUserHasJoined(Principal principal) {
        return ResponseEntity.ok(chatService.getListOfAllChatRoomsUserHasJoined(userService.getUserInfo(principal.getName()).getUsername()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<Boolean> createChatRoom(Principal principal, @RequestBody ChatRoomDTO chatRoom) {
        log.warn(chatRoom.toString());
        return ResponseEntity.ok(chatService.createChatRoom(principal.getName(), chatRoom));
    }

}
